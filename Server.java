//Major Agota-Piroska
//maim1846
//523

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Server {
	private int height = 1080, width = 1920;
	private double xOffset;
	public BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	public int clientNr;		
	
	private Server(int clientNr) throws Exception {
		this.clientNr = clientNr;
		xOffset = 4.0/clientNr;
		ServerSocket server = new ServerSocket(5000);
        System.out.println("Elindult a szerver!");
        
        int nr = 0;
        while(nr < clientNr-1){
            Socket socket = server.accept();
            Threads thr = new Threads(socket, nr, width/clientNr, xOffset, nr*xOffset-2);
            System.out.println(nr);
            thr.start();
            nr++;
        }
        Socket socket = server.accept();
        Threads thr = new Threads(socket, nr, width/clientNr+width%clientNr, xOffset, nr*xOffset-2);
        System.out.println(nr);
        thr.run();
        System.out.println("Vege");
        
        BufferedImage[] img = new BufferedImage[clientNr];
        for (int i = 0; i < clientNr; i++) {
        	img[i] = ImageIO.read(new File("mandelbrot"+i+".png"));
        }
        
        Graphics2D g2 = image.createGraphics();
        int wCount = 0;
        for (int i = 0; i < clientNr; i++) {
        	g2.drawImage(img[i], null, wCount, 0);
        	wCount += img[i].getWidth();
        }
        
        g2.dispose();
        
        ImageIO.write(image, "png", new File("mandelbrot.png"));
        
        
	}
	
	public static void main(String args[]) throws Exception {
		int n=4;
		try {
			n=Integer.parseInt(args[0]);
			if (n<4) {
				n=4;
				System.out.println("a javasolt szam tul kicsi n=4 lesz");
			}
			
		}catch(NumberFormatException e){
			System.out.println("n=4 marad mivel a javaslat nem megfelelo");
		}catch(Exception e) {
			System.out.println("n=4 nem volt megadva semmi");
		}
		Server server = new Server(n);
    }
}
