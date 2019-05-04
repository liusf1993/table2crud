package com.hqjl.table2crud.ui;

import com.hqjl.table2crud.constant.ColumnEnum;
import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.db.DbReader;
import com.hqjl.table2crud.db.TableDesc;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.DBSettings;
import com.hqjl.table2crud.domain.Encoding;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.domain.SqlInfo;
import com.hqjl.table2crud.generator.GeneratorAdaptor;
import com.hqjl.table2crud.storage.Env;
import com.hqjl.table2crud.storage.PluginProjectConfigHolder;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.storage.domain.PluginProjectConfig;
import com.hqjl.table2crud.threadvar.ThreadVariablesHolder;
import com.hqjl.table2crud.util.SqlAnaly;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.SystemIndependent;

public class GeneratorMainDialog extends JDialog {


  private static final Logger logger = Logger.getInstance(GeneratorMainDialog.class);
  private static final long serialVersionUID = -1783629807073084146L;
  /**
   * header info
   */
  private static String[] COLUMN_NAMES = ColumnEnum.getColumnNames();
  private final FileChooserDescriptor chooseFolderOnlyDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
  private Color background = new JBColor(new Color(60, 63, 65), new Color(60, 63, 65));
  private JBTable columnsTable;
  private JBTable datasourceTables;
  private String path;
  /**
   * overall
   */
  private JPanel contentPanel;
  private JTextPane outputMsg;
  /**
   * input
   */
  private JTabbedPane corePanel;
  private JPanel ddlPanel;
  private JTextArea ddlInput;
  private JButton analysisBtn;
  /**
   * generator
   */
  private JPanel generatorPanel;
  private JButton BTN_dao;
  private JButton BTN_bo;
  private JButton BTN_transfer;
  private JButton BTN_manager;
  private JButton BTN_managerImpl;
  private JButton poBtn;
  private JButton generate;
  private JComboBox codeSelectedBox;
  private JComboBox encodingSelectedBox;
  private JTextField pathTxt;
  private JButton BTN_selPath;
  private JCheckBox overwriteChx;

  private JCheckBox CHK_daoUseSeq;
  private JCheckBox CHK_managerUseBO;
  private JCheckBox selectAll;
  private JScrollPane columnsPanel;
  private JTextField tableNameTxt;
  private JButton BTN_basedao;
  private JButton BTN_basequery;
  /**
   * settings
   */
  private JPanel settingPanel;
  private JLabel TEXT_name;
  private JLabel TEXT_encoding;
  private JLabel TEXT_code;
  private JRadioButton RADIO_languageChs;
  private JRadioButton RADIO_languageEn;
  private JRadioButton RADIO_encodeUTF8;
  private JRadioButton RADIO_encodeGBK;
  private JRadioButton RADIO_codeJava;
  private JButton applySettingBtn;
  private JTextField authorTxt;
  private JTextField dbUrlTxt;
  private JLabel TEXT_useCustomTpl;
  private JButton BTN_selTplPath;
  /**
   * Notice
   */
  private JButton BTN_go;
  private JTextField INPUT_tddl_url;
  private JButton BTN_DAOXml;
  private JButton BTN_SQLMapConfigXml;
  private JButton BTN_PersistenceXml;
  /**
   * about
   */
  private Column primaryColumn;
  private JTextField divisionFields;
  private JCheckBox returnCollectionCheckBox;
  private JCheckBox poCheck;
  private JCheckBox daoCheck;
  private JCheckBox managerCheck;
  private JCheckBox serviceCheck;
  private JCheckBox serviceImplCheck;
  private JCheckBox controllerCheck;
  private JCheckBox managerImplCheck;
  private JLabel passswdLabel;
  private JTextField dbUsernameTxt;
  private JTextField dbDatabaseTxt;
  private JPasswordField dbPasswdTxt;
  private JTextField tableConditionTxt;
  private JButton showTablesBtn;
  private JButton datasourceAnalysisBtn;
  private JScrollPane tablesPanel;
  private JLabel dbUrlLabel;
  private JPanel datasourcePanel;
  private JTextField sliceFields;
  private AnActionEvent event;

  public GeneratorMainDialog(AnActionEvent event) {
    this.event = event;
    init();
  }


  private synchronized void onGeneratorClick() {
    try {
      generate.setEnabled(false);
      String divisionFields = this.divisionFields.getText();
      String sliceFields = this.sliceFields.getText();
      ThreadVariablesHolder.setDivisionFields(divisionFields);
      ThreadVariablesHolder.setSliceFields(sliceFields);
      generate();
      corePanel.setSelectedComponent(ddlPanel);
      //this.divisionFields.setText("");
      //CHK_overwrite.setSelected(false);
      this.setVisible(false);
    } catch (Exception e1) {
      e1.printStackTrace();
      dealException(e1);
    } finally {
      generate.setEnabled(true);
    }
  }


  private void generate() {
    checkOutputPath();
    if (columnsTable == null) {
      throw new RuntimeException("please input sql at Input TAB and click analysis!");
    }
    String code = (String) codeSelectedBox.getSelectedItem();
    String encoding = (String) encodingSelectedBox.getSelectedItem();
    //获取表格中的数据
    List<Column> poColumnList = getDOColumns();
    ThreadVariablesHolder.setColumns(poColumnList);
    ThreadVariablesHolder.setUseCollection(returnCollectionCheckBox.isSelected());
    String tableName = this.tableNameTxt.getText();
    if (StringUtils.isBlank(tableName)) {
      throw new RuntimeException("table name needed to set!");
    }

    if (StringUtils.isBlank(code) || StringUtils.isBlank(encoding)) {
      throw new RuntimeException("code and encoding need to select!");
    }

    //先保存关于prj的配置
    Setting settingSave = new Setting(Encoding.get(encoding), Code.get(code), path);
    settingSave.setSwitch(false, false, false, false,
        overwriteChx.isSelected(), false);
    SettingManager.applyPrj(settingSave);
    Setting setting = SettingManager.get();
    if (setting.getPath().isEmpty()) {
      dealMessage("path can not be empty", false);
      return;
    }
    Env.encodeFrom = Env.getProjectCharset();
    Env.encodeTo = Charset.forName(encoding);
    GeneratorAdaptor generatorAdaptor = new GeneratorAdaptor(tableName, path, code);
    generatorAdaptor.setColumnList(poColumnList, new ArrayList<>());
    generatorAdaptor.setPrimaryColumn(primaryColumn);
    boolean success = false;
    StringBuilder resultMsg = new StringBuilder();
    List<GenerateType> generateTypes = getGenerateTypes();
    for (GenerateType generateType : generateTypes) {
      success = generatorAdaptor.generate(generateType);
      resultMsg = appendResultMsg(resultMsg, success, generateType.name());
    }
    dealMessage("generate ALL done" + (resultMsg.length() > 0 ? ", But generate " + resultMsg + " error!" : ""), success);
  }

  private List<GenerateType> getGenerateTypes() {

    List<GenerateType> generateTypes = new ArrayList<>();
    if (poCheck.isSelected()) {
      generateTypes.add(GenerateType.PO);
    }
    if (daoCheck.isSelected()) {
      generateTypes.add(GenerateType.DAO);
    }
    if (managerCheck.isSelected()) {
      generateTypes.add(GenerateType.Manager);
    }
    if (managerImplCheck.isSelected()) {
      generateTypes.add(GenerateType.ManagerImpl);
    }
    if (serviceCheck.isSelected()) {
      generateTypes.add(GenerateType.Service);
    }
    if (serviceImplCheck.isSelected()) {
      generateTypes.add(GenerateType.ServiceImpl);
    }
    if (controllerCheck.isSelected()) {
      generateTypes.add(GenerateType.ReqVO);
      generateTypes.add(GenerateType.ResVO);
      generateTypes.add(GenerateType.Transfer);
      generateTypes.add(GenerateType.Controller);
      generateTypes.add(GenerateType.ReqDelVO);
    }
    return generateTypes;
  }

  private StringBuilder appendResultMsg(StringBuilder originMsg, boolean success, String msgToAppend) {
    if (success) {
      return originMsg;
    }
    if (originMsg.length() > 0) {
      originMsg.append(", ");
    }
    originMsg.append(msgToAppend);
    return originMsg;
  }


  private List<Column> getDOColumns() {
    return getColumns(ColumnEnum.PO);
  }


  private List<Column> getColumns(ColumnEnum columnEnum) {
    List<Column> list = new ArrayList<Column>();
    TableModel tm = columnsTable.getModel();
    for (int i = 0; i < tm.getRowCount(); i++) {
      Boolean selected = (Boolean) tm.getValueAt(i, columnEnum.getOrder());
      if (selected) {
        try {
          Column c = new Column();
          c.setName(((String) tm.getValueAt(i, ColumnEnum.Column.getOrder())).toUpperCase());
          c.setProperty((String) tm.getValueAt(i, ColumnEnum.ClassProperty.getOrder()));
          c.setType((String) tm.getValueAt(i, ColumnEnum.CodeType.getOrder()));
          c.setPrimaryKey("Y".equals(tm.getValueAt(i, ColumnEnum.Primary.getOrder())));
          String comment = (String) tm.getValueAt(i, ColumnEnum.Comment.getOrder());
          c.setComment(StringUtils.isBlank(comment) ? c.getProperty() : comment);
          c.setDbField(String.valueOf(tm.getValueAt(i, ColumnEnum.Column.getOrder())));
          if (c.getPrimaryKey()) {
            primaryColumn = c;
          }
          list.add(c);
        } catch (Exception e) {
          dealException(e);
        }
      }
    }
    return list;
  }

  private void checkOutputPath() {
    path = pathTxt.getText();
    if (path.isEmpty()) {
      throw new RuntimeException(" path can not be null");
    }
  }


  private void applySettings(Setting setting) {
    pathTxt.setText(setting.getPath());
    RADIO_codeJava.setSelected(true);
    codeSelectedBox.setSelectedItem("JAVA");
    RADIO_encodeUTF8.setSelected(true);
    encodingSelectedBox.setSelectedItem("UTF-8");
  }

  private synchronized void onAnalysis() {
    try {
      analysisBtn.setEnabled(false);
      String text = ddlInput.getText();
      if (StringUtils.isBlank(text)) {
        throw new RuntimeException("no sql detected!");
      }
      SqlInfo sqlInfo = SqlAnaly.analysis(text);
      if (sqlInfo.isValid()) {
        PluginProjectConfig pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig();
        if (pluginProjectConfig != null) {
          pluginProjectConfig.setSql(text);
        }
        renewGeneratorPanel(sqlInfo);
        corePanel.setSelectedComponent(generatorPanel);
        dealMessage("analysis finished", true);
      } else {
        dealMessage("no sql detected or sql is not supported!", false);
      }
    } catch (Exception e) {
      dealException(e);
    } finally {
      analysisBtn.setEnabled(true);
    }
  }


  private synchronized void analysisFromDataSourceTable() {
    try {
      TableModel tablesModel = datasourceTables.getModel();
      String tableName = "";
      for (int row = 0; row < tablesModel.getRowCount(); row++) {
        if (tablesModel.getValueAt(row, 1) == Boolean.TRUE) {
          tableName = (String) tablesModel.getValueAt(row, 0);
          break;
        }
      }
      if (tableName.isEmpty()) {
        dealMessage("Please select at least one table ", false);
        return;
      }
      DbReader dbReader = getDbReader();
      TableDesc[] tableDescs = dbReader.readColumns(tableName);
      List<Column> columns = Arrays.stream(tableDescs).map(x -> new Column(x.field(), x.fieldType(), x.primary()).setComment(x.comment()))
          .collect(
              Collectors.toList());
      TableDesc tableDesc = Arrays.stream(tableDescs).filter(TableDesc::primary).findFirst()
          .orElseThrow(() -> new RuntimeException("primary key not exists"));
      renewGeneratorPanel(tableName, columns, tableDesc.field());
      corePanel.setSelectedComponent(generatorPanel);
      dealMessage("analysis finished");
    } catch (Exception e) {
      dealException(e);
    } finally {
      analysisBtn.setEnabled(true);
    }

  }

  private void renewGeneratorPanel(SqlInfo sqlInfo) {
    renewGeneratorPanel(sqlInfo.getTableName(), sqlInfo.getColumnList(), sqlInfo.getPrimaryKey());
  }

  private void renewGeneratorPanel(String tableName, List<Column> columnList, String primaryKey) {
    int num = 0;
    List<Object[]> rows = new ArrayList<>();
    for (Column c : columnList) {
      List<Object> rowColumns = new ArrayList<>();
      num++;
      c.setPrimaryKey(StringUtils.equalsIgnoreCase(primaryKey, c.getName()));
      rowColumns.add(num);
      rowColumns.add(Boolean.TRUE);
      rowColumns.add(c.getName());
      rowColumns.add(c.getTypeStr());
      rowColumns.add(c.getType());
      rowColumns.add(c.getProperty());
      rowColumns.add(c.getPrimaryStr());
      rowColumns.add(c.getComment());
      rows.add(rowColumns.toArray(new Object[]{rowColumns.size()}));
    }
    columnsPanel.setSize(columnsPanel.getWidth(), columnsPanel.getHeight() * columnList.size() / 4);
    Object[][] dataSet = rows.toArray(new Object[][]{new Object[]{rows.size()}});

    TableModel model = new DefaultTableModel(dataSet, COLUMN_NAMES) {
      private static final long serialVersionUID = 1594532590117462977L;

      @Override
      public boolean isCellEditable(int rowIndex, int colIndex) {
        //相应的列不可编辑
        return !ArrayUtils.contains(new int[]{0, 2, 3, 6}, colIndex);
      }
    };
    columnsTable = new JBTable(model);
    columnsTable.setBackground(background);
    columnsTable.getTableHeader().setReorderingAllowed(false);
    //No.列尝试不同的前景色
    TableColumn tableColumn = columnsTable.getColumn(ColumnEnum.NO.getName());
    DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
    cellRender.setHorizontalAlignment(SwingConstants.CENTER);
    tableColumn.setCellRenderer(cellRender);
    tableColumn.setPreferredWidth(30);
    //DO列是checkbox
    TableColumn tableColumnDO = columnsTable.getColumn(ColumnEnum.PO.getName());
    tableColumnDO.setCellEditor(new CheckBoxCellEditor());
    tableColumnDO.setCellRenderer(new CheckBoxRenderer());
    tableColumnDO.setPreferredWidth(30);

    TableColumn tableColumnPrimary = columnsTable.getColumn(ColumnEnum.Primary.getName());
    DefaultTableCellRenderer cellRender1 = new DefaultTableCellRenderer();
    cellRender1.setHorizontalAlignment(SwingConstants.CENTER);
    tableColumnPrimary.setPreferredWidth(20);
    tableColumnPrimary.setCellRenderer(cellRender1);

    columnsPanel.getViewport().add(columnsTable);
    tableNameTxt.setText(tableName);

  }


  private void onCancel() {
    dispose();
  }


  private void init() {
    initSettings();
    initEventListener();

  }

  private void initSettings() {
    PluginProjectConfig pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig();
    String path = pluginProjectConfig.getPath();
    @SystemIndependent String basePath = Objects.requireNonNull(event.getProject()).getBasePath();
    if(!String.valueOf(path).startsWith(basePath)){
      pluginProjectConfig.setPath(basePath);
    }

    setContentPane(contentPanel);
    getRootPane().setDefaultButton(analysisBtn);
    setBackground(background);
    if (pluginProjectConfig != null) {
      ddlInput.setText(pluginProjectConfig.sql());
    }
    final Setting setting1 = SettingManager.get();
    applySettings(setting1);
    Setting setting = SettingManager.get();
    initDBSetting(setting);
  }

  private void initDBSetting(Setting setting) {
    if (setting == null) {
      return;
    }
    DBSettings dbSettings = setting.getDbSettings();
    dbDatabaseTxt.setText(dbSettings.db());
    dbUsernameTxt.setText(dbSettings.username());
    dbPasswdTxt.setText(dbSettings.password());
    dbUrlTxt.setText(dbSettings.url());
  }

  private void initEventListener() {
    analysisBtn.addActionListener(e -> onAnalysis());

    BTN_selPath.addActionListener(e -> {
      chooseFolderOnlyDescriptor.setTitle("Select Path");
      chooseFolderOnlyDescriptor.setDescription("Select Path To Generate, generally we choose the biz path to generate");
      VirtualFile file = FileChooser.chooseFile(chooseFolderOnlyDescriptor, Env.project, null);
      if (file != null) {
        pathTxt.setText(file.getPath());
        dealMessage("");
        SettingManager.applyPrjPath(file.getPath());
        System.out.println(file.getPath());
      }
    });

    selectAll.addActionListener(e -> {
      if (selectAll.isSelected()) {
        poCheck.setSelected(true);
        daoCheck.setSelected(true);
        managerCheck.setSelected(true);
        managerImplCheck.setSelected(true);
        serviceCheck.setSelected(true);
        serviceImplCheck.setSelected(true);
        controllerCheck.setSelected(true);
      } else {
        poCheck.setSelected(false);
        daoCheck.setSelected(false);
        managerCheck.setSelected(false);
        managerImplCheck.setSelected(false);
        serviceCheck.setSelected(false);
        serviceImplCheck.setSelected(false);
        controllerCheck.setSelected(false);
      }
    });

    codeSelectedBox.addActionListener(e -> {
      String code = (String) codeSelectedBox.getSelectedItem();
      SettingManager.applyPrjCode(code);
    });

    encodingSelectedBox.addActionListener(e -> {
      String encoding = (String) encodingSelectedBox.getSelectedItem();
      SettingManager.applyPrjEncoding(encoding);
    });

    //checkbox的save
    ActionListener saveSwitches = e -> SettingManager.applySwitch(false, false, false,
        selectAll.isSelected(), overwriteChx.isSelected(), false);

    overwriteChx.addActionListener(saveSwitches);

    //step2 - generate codes

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    datasourceAnalysisBtn.addActionListener((e) -> analysisFromDataSourceTable());
    applySettingBtn.addActionListener((e) -> onApplySettings());
    showTablesBtn.addActionListener((e) -> readTables());
    initGlobalEvent();

    generate.addActionListener((e) -> onGeneratorClick());
    registerEnterEvent(datasourcePanel, showTablesBtn);
    registerEnterEvent(tableConditionTxt, showTablesBtn);
    registerEnterEvent(tablesPanel, datasourceAnalysisBtn);
    registerEnterEvent(ddlPanel, analysisBtn);
    registerEnterEvent(settingPanel, applySettingBtn);


  }

  private void initGlobalEvent() {
    contentPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    contentPanel.registerKeyboardAction(e -> {
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });
  }

  private void registerEnterEvent(JComponent jComponent, JButton btn) {
    jComponent.registerKeyboardAction((e) -> btn.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  private synchronized void readTables() {
    try {
      clearOutputMsg();
      tablesPanel.getViewport().removeAll();
      String tableLike = tableConditionTxt.getText().toLowerCase();
      DbReader dbReader = getDbReader();
      Object[][] data = dbReader.readTables(tableLike);
      TableModel model = new DefaultTableModel(data, new String[]{"table name", "selected"});
      JBTable tables = new JBTable(model);
      this.datasourceTables = tables;
      TableColumn tableColumnDO = tables.getColumn("selected");
      tableColumnDO.setCellEditor(new CheckBoxCellEditor());
      tableColumnDO.setCellRenderer(new CheckBoxRenderer());
      tableColumnDO.setPreferredWidth(30);
      tablesPanel.getViewport().add(tables);
    } catch (Exception e) {
      dealException(e);
      corePanel.setSelectedComponent(settingPanel);
    }
  }


  private synchronized void onApplySettings() {
    if (!applySettingBtn.isEnabled()) {
      return;
    }
    try {
      this.applySettingBtn.setEnabled(false);
      clearOutputMsg();
      String author = authorTxt.getText();
      Encoding encoding = Encoding.UTF8;

      Code code = Code.JAVA;
      Setting settings = new Setting(author, encoding, code);
      DBSettings dbSettings = DBSettings
          .apply(dbUrlTxt.getText(), dbUsernameTxt.getText(), new String(dbPasswdTxt.getPassword()), dbDatabaseTxt.getText());
      settings.setDbSettings(dbSettings);
      SettingManager.applyApp(settings);
      getDbReader();
      dealMessage("apply setting success!");
      corePanel.setSelectedComponent(datasourcePanel);
    } catch (Exception e) {
      dealException(e);
    } finally {
      this.applySettingBtn.setEnabled(true);
    }


  }


  private DbReader getDbReader() {
    return DbReader
        .apply(dbUrlTxt.getText(), dbUsernameTxt.getText(), new String(dbPasswdTxt.getPassword()), dbDatabaseTxt.getText());
  }

  private void dealException(Throwable e) {
    e.printStackTrace();
    StringBuilder sb = new StringBuilder();
    while (e != null) {
      sb.append(e.getMessage()).append("\n");
      e = e.getCause();
    }
    dealMessage(sb.toString(), false);
  }

  private void dealMessage(String s) {
    dealMessage(s, true);
  }

  private void clearOutputMsg() {
    outputMsg.setText("");
  }

  private void dealMessage(String message, boolean success) {
    outputMsg.setText(message);
    if (success) {
      outputMsg.setForeground(JBColor.GREEN);
    } else {
      outputMsg.setForeground(JBColor.RED);
    }
  }


}
