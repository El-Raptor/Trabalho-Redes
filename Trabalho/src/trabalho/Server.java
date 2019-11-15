package trabalho;

import java.net.*;

import java.io.*;

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
				
				String operation[] = receivedMessage.split("|");
				
				// Multiplicando o número por dois e formando a mensagem de retorno.
				String returnMessage;
				try {
					File directory = new File(operation[1]);
					directory.mkdir();
					returnMessage = "Diretório criado\n";
				} catch (Exception e) {
					// Entrada não era um número. Enviando a mensagem correta ao cliente.
					returnMessage = "Falha na criação do diretório.\n";
				}

				// Enviando a resposta de volta ao cliente.
				OutputStream out = socket.getOutputStream();
				OutputStreamWriter outWriter = new OutputStreamWriter(out);
				BufferedWriter bw = new BufferedWriter(outWriter);
				bw.write(returnMessage);
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
 