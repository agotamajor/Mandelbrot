//Major Agota-Piroska
//maim1846
//523

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public static void main(String args[]) throws Exception {
		String localhost = InetAddress.getLocalHost().getHostName();
		Socket clientSocket = new Socket("localhost", 5000);
		DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String szam = in.readLine();
		double xStart = Double.parseDouble(szam);
		szam = in.readLine();
		int width = Integer.parseInt(szam);
		szam = in.readLine();
		double offset = Double.parseDouble(szam);
		int height = 1080, max = 100;
        String matrix;
        for (int row = 0; row < height; row++) {
        	matrix = "";
        	for (int col = 0; col < width; col++) {
                double c_re = (col - width/2)*offset/width+xStart;
                double c_im = (row - height/2)*offset/width;
                double x = 0, y = 0;
                int iterations = 0;
                while (x*x+y*y < 4 && iterations < max) {
                    double x_new = x*x-y*y+c_re;
                    y = 2*x*y+c_im;
                    x = x_new;
                    iterations++;
                } 
                if (iterations < max) {
                	matrix += " 0";  
                }
                else {
                	matrix += " 1";  
                }
            }
        	matrix += "\n";
        	out.writeBytes(matrix);
        }
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("vege");
	}
}
