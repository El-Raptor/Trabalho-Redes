package trabalho;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Socket socket;

	public static void main(String args[]) {
		try {
			String host = "localhost";
			int port = 6796;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);

			// Envia a mensagem ao servidor.
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter outWriter = new OutputStreamWriter(out);
			BufferedWriter bw = new BufferedWriter(outWriter);

			System.out.println("Digite a opera��o na qual deseja realizar:\n\nCriar diret�rio: criar\nRemover "
					+ "diret�rio: remvdir\nListar conte�do de diret�rio: listar\nEnviar arquivo: enviar\nRemover arquivo: remvarq");

			// L� do teclado a opera��o desejada.
			Scanner scanner = new Scanner(System.in);
			String operation = scanner.nextLine();

			// Transforma todos os caracteres em min�sculo.
			operation = operation.toLowerCase();

			String path = "src\\Servidor\\";
			

			if (operation.equals("criar")) {
				System.out.println("Digite um nome para a nova pasta.");
				path = path + scanner.nextLine();
			} else if (operation.equals("remvdir")) {
				System.out.println("Digite o diret�rio o qual deseja remover.");
				String desiredDir = scanner.nextLine();
				
				// Impede a exclus�o do diret�rio "Servidor".
				if (desiredDir.equals("\n") || desiredDir.equals("")) { 
					scanner.close();
					throw new IllegalArgumentException("N�o � permitido apagar este diret�rio.");
				}			
				path = path + desiredDir;
				
			} else if (operation.equals("enviar")) {
				// TODO;
			} else if (operation.equals("remvarq")) {
				// TODO;
			}
			
			String sendMessage = operation + ":" + path + "\n";

			// Fecha o scanner.
			scanner.close();

			bw.write(sendMessage);
			bw.flush();
			
			System.out.println("\nMensagem enviada ao servidor: " + sendMessage + "\n");

			// Recebe a mensagem de retorno do servidor.
			InputStream in = socket.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inReader);
			String message = br.readLine();
			
			// Trata e formata a String para um formato de lista.
			message = message.replace(";", "\n");
			System.out.println(message);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			// Closing the socket
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}