package rsqbexpt;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RSQBApplication {
	private ExptFrame frame;
	/**
	 * @param args
	 */
	public RSQBApplication() {

		GraphicsDevice device;
		frame = new ExptFrame();
		frame.setVisible(true);
		device = 
			GraphicsEnvironment.
			getLocalGraphicsEnvironment().
			getDefaultScreenDevice();
		if ( device.isFullScreenSupported() ) { 
			device.setFullScreenWindow(frame);
			frame.setVisible(true);
		}
		else { 
			System.err.println("Full screen not supported"); 
		}



	}

}
