package com.hqjl.table2crud.ui;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

public class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {

  private static final long serialVersionUID = 1L;
  protected JCheckBox checkBox;

  public CheckBoxCellEditor() {
    checkBox = new JCheckBox();
    checkBox.setHorizontalAlignment(SwingConstants.CENTER);
  }

  @Override
  public Object getCellEditorValue() {
    return checkBox.isSelected();
  }

  @Override
  public Component getTableCellEditorComponent(
      JTable table,
      Object value,
      boolean isSelected,
      int row,
      int column) {
    checkBox.setSelected((Boolean) value);
    return checkBox;
  }
}
