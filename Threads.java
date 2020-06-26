//Major Agota-Piroska
//maim1846
//523

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Threads extends Thread {
	private Socket socket;
	private int nr;
	private BufferedReader in;
	private DataOutputStream out;
	public BufferedImage image;
	private int height;
	private int width;
	private double offset;
	private double xStart;
	
	public Threads(Socket s, int nr, int width, double offset, double xStart){
        socket = new Socket();
        socket = s;
        height = 1080;
        this.offset = offset;
        this.nr = nr; 
        this.width = width;
        this.xStart = xStart;
        
        image = new BufferedImage(width+1, height, BufferedImage.TYPE_INT_RGB);
    }
	
	public void run() {
		int black = 0x000000, white = 0xFFFFFF;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.writeBytes(xStart+"\n");
			out.writeBytes(width+"\n");
			out.writeBytes(offset+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String valasz = "";
		int row = 0;
		for (int i = 0; i < height; i++) {
			int col = 0;
			try {
				valasz = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] tokens = valasz.split(" ");
			for (String token: tokens) {
				if (token.equals("1")) {
					image.setRGB(col, row, black);
				} else {
					image.setRGB(col, row, white);
				}
				col++;
			}
			row++;
		}
		
		try {
			ImageIO.write(image, "png", new File("mandelbrot"+nr+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
