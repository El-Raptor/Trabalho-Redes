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
				
				String operation[] = receivedMessage.split(":");
				
				String returnMessage = "Falha na opera��o.";
				
				// Seleciona a opera��o escolhida pelo cliente.				
				if (operation[0].equals("criar")) {
					try {
						File directory = new File(operation[1]);
						directory.mkdir();
						returnMessage = "Diret�rio " + operation[1] + " criado\n";
					} catch (Exception e) {
						// Entrada n�o era um n�mero. Enviando a mensagem correta ao cliente.
						returnMessage = "Falha na cria��o do diret�rio.\n";
					}
				} else if (operation[0].equals("listar")) {
					try {
						File directory = new File(operation[1]);
						returnMessage = "";
						
						// Transforma o vetor de String em uma String.
						for (int i = 0; i < directory.list().length; i++)
							returnMessage += directory.list()[i] + ";";
						
					} catch (Exception e) {
						// Erro de opera��o.
					}
				} else if (operation[0].equals("remvdir")) {
					// TODO;
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
 