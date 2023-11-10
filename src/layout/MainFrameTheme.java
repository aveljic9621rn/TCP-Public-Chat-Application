package layout;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class MainFrameTheme extends DefaultMetalTheme {
	public ColorUIResource getWindowTitleInactiveBackground() {
		return new ColorUIResource(Color.orange);
	}

	public ColorUIResource getWindowTitleBackground() {
	  	return new ColorUIResource(new Color(104, 163, 151));
	}

	public ColorUIResource getPrimaryControlHighlight() {
		return new ColorUIResource(new Color(104, 163, 151));
	}
	
	public ColorUIResource getPrimaryControlDarkShadow() {
	  	return new ColorUIResource(new Color(104, 163, 151));
	}

	public ColorUIResource getPrimaryControl() {
		return new ColorUIResource(Color.orange);
	}

	public ColorUIResource getControlHighlight() {
		return new ColorUIResource(Color.orange);
	}

	public ColorUIResource getControlDarkShadow() {
		return new ColorUIResource(Color.orange);
	}

	public ColorUIResource getControl() {
		return new ColorUIResource(Color.orange);
	}
}