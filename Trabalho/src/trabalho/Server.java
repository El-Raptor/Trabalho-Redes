package trabalho;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS");

				// Lendo a mensagem do cliente.
				socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(inReader);
				String receivedMessage = br.readLine();
				Date receivedMessageTime = new Date();
				System.out.println("(" + dateFormat.format(receivedMessageTime) + "): Mensagem recebida do cliente: "
						+ receivedMessage);

				// Separa a String recebida pelo cliente em duas:
				// A primeira para o comando e a segunda para o caminho.
				String operation[] = receivedMessage.split(":");

				// Recupera hora do sistema.

				Date sendMessageTime = new Date();

				String returnMessage = "(" + dateFormat.format(sendMessageTime) + "): ";

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

					returnMessage += "Itens:;";

					// Transforma o vetor de String em uma String.
					for (int i = 0; i < directory.list().length; i++)
						returnMessage += directory.list()[i] + ";";

				} else if (operation[0].equals("remover")) {

					File dir = new File(operation[1]);
					boolean dirDeleted = dir.delete();

					// Verifica se o diretório foi deletado
					if (dirDeleted)
						returnMessage = "Diretório/arquivo deletado.\n";

					else
						returnMessage += "Falha na deleção de diretório/arquivo.\n";

				} else if (operation[0].equals("enviar")) {
					// Preparando o arquivo.
					File dir = new File(operation[1]);
					byte[] buffer = new byte[5120];

					// Lê o arquivo.
					FileInputStream fileIn = new FileInputStream(dir);
					fileIn.read(buffer, 0, buffer.length);

					// Escreve o arquivo no socket.
					OutputStream outFile = socket.getOutputStream();
					outFile.write(buffer, 0, buffer.length);
					outFile.flush();

					fileIn.close();

				}
				
				System.out.println(receivedMessage);
				
				// Enviando a resposta de volta ao cliente.
				OutputStream out = socket.getOutputStream();
				OutputStreamWriter outWriter = new OutputStreamWriter(out);
				BufferedWriter bw = new BufferedWriter(outWriter);
				// Escreve no Socket
				bw.write(returnMessage + "\n");
				System.out.println("(" + dateFormat.format(sendMessageTime) + "): Mensagem retornada ao cliente.");
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
