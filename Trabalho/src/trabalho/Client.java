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

			System.out.println("Digite a operação na qual deseja realizar:\n\nCriar diretório: criar\nRemover "
					+ "diretório: remvdir\nListar conteúdo de diretório: listar\nEnviar arquivo: enviar\nRemover arquivo: remvarq");

			// Lê do teclado a operação desejada.
			Scanner scanner = new Scanner(System.in);
			String operation = scanner.nextLine();
			
			System.out.println("Digite um nome para a nova pasta.");
			String path = "src\\Servidor\\" + scanner.nextLine();
			
			// Transforma todos os caracteres em minúsculo.
			operation = operation.toLowerCase();

			// Fecha o scanner.
			scanner.close();

			String sendMessage = operation + " " + path + "\n";
			bw.write(sendMessage);
			bw.flush();

			System.out.println("Mensagem enviada ao servidor: " + sendMessage);

			// Recebe a mensagem de retorno do servidor.
			InputStream in = socket.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inReader);
			String message = br.readLine();
			System.out.println("Mensagem recebida do servidor: " + message);
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