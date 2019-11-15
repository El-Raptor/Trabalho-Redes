package trabalho;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

			System.out.println("Digite a operação na qual deseja realizar:\n\nCriar diretório: criar\nRemover "
					+ "diretório/arquivo: remover\nListar conteúdo de diretório: listar\nEnviar arquivo: enviar\n");

			// Lê do teclado a operação desejada.
			Scanner scanner = new Scanner(System.in);
			String operation = scanner.nextLine();

			// Transforma todos os caracteres em minúsculo.
			operation = operation.toLowerCase();

			String path = "src\\Servidor\\";
			

			if (operation.equals("criar")) {
				System.out.println("Digite um nome para a nova pasta.");
				path = path + scanner.nextLine();
			} else if (operation.equals("remover")) {
				System.out.println("Digite o diretório/arquivo no qual deseja remover.");
				String desiredDir = scanner.nextLine();
				
				// Impede a exclusão do diretório "Servidor".
				if (desiredDir.equals("\n") || desiredDir.equals("")) { 
					scanner.close();
					throw new IllegalArgumentException("Não é permitido apagar este diretório.");
				}			
				path = path + desiredDir;
				
			} else if (operation.equals("enviar")) {
				// TODO;
			} 
			
			String sendMessage = operation + ":" + path + "\n";

			// Fecha o scanner.
			scanner.close();

			bw.write(sendMessage);
			bw.flush();
			
			// Recupera hora do sistema.
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS");
			Date date = new Date();
			
			System.out.println("\n(" + dateFormat.format(date) + "): Mensagem enviada ao servidor: " + sendMessage /*+ "\n"*/);

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