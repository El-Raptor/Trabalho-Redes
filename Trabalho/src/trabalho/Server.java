package trabalho;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

	private static Socket socket;

	public static void main(String args[]) {
		try {
			int port = 6796;
			ServerSocket serverSocket = new ServerSocket(port);

			System.out.println("Servidor aberto ouvindo a porta " + port + ".");
			System.out.println("Esperando cliente...");

			// Servidor sempre rodando.
			while (true) {
				// Lendo a mensagem do cliente.
				socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(inReader);
				String receivedMessage = br.readLine();
				System.out.println("Mensagem recebida do cliente: " + receivedMessage);

				// Separa a String recebida pelo cliente em duas:
				// A primeira para o comando e a segunda para o caminho.
				String operation[] = receivedMessage.split(":");

				// Recupera hora do sistema.
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				
				String returnMessage = "(" + dateFormat.format(date) + "): ";

				// Seleciona a operação escolhida pelo cliente.
				if (operation[0].equals("criar")) {
					File directory = new File(operation[1]);
					boolean dirCreated = directory.mkdir();

					// Verifica se diretório foi criado.
					if (dirCreated)
						returnMessage += "Diretório " + operation[1] + " criado\n";

					else
						returnMessage += "Falha na criação de diretório.\n";

				} else if (operation[0].equals("listar")) {

					File directory = new File(operation[1]);
					//returnMessage = "";

					// Transforma o vetor de String em uma String.
					for (int i = 0; i < directory.list().length; i++)
						returnMessage += directory.list()[i] + ";";

				} else if (operation[0].equals("remvdir")) {

					File dir = new File(operation[1]);
					boolean dirDeleted = dir.delete();

					// Verifica se o diretório foi deletado
					if (dirDeleted)
						returnMessage = "Diretório deletado.\n";
					
					else
						returnMessage += "Falha na deleção de diretório.\n";

				} else if (operation[0].equals("enviar")) {
					// TODO;
				} else if (operation[0].equals("remvarq")) {
					// TODO;
				}

				// Enviando a resposta de volta ao cliente.
				OutputStream out = socket.getOutputStream();
				OutputStreamWriter outWriter = new OutputStreamWriter(out);
				BufferedWriter bw = new BufferedWriter(outWriter);
				bw.write(returnMessage + "\n");
				System.out.println(returnMessage);
				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				// Socket failed closing.
			}
		}
	}
}
