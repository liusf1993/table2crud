package com.hqjl.table2crud.ui;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

  private static final long serialVersionUID = 1L;

  public CheckBoxRenderer() {
    super();
    setOpaque(true);
    setHorizontalAlignment(SwingConstants.CENTER);
  }

  @Override
  public Component getTableCellRendererComponent(
      JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column) {
    if (value instanceof Boolean) {
      setSelected((Boolean) value);
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }
    return this;
  }
}
