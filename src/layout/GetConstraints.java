package layout;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GetConstraints {
	public static GridBagConstraints getConstraints(int x, int y) {
		GridBagConstraints constrain = new GridBagConstraints();
		constrain.gridx = x;
		constrain.gridy = y;
		constrain.insets = new Insets(20, 20, 0, 0);
		constrain.anchor = GridBagConstraints.WEST;
		return constrain;
	}
}
