package test.mapoutput;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.ui.MapControl;

/**
 * <p>
 * Title:地图输出
 * </p>
 * 
 * <p>
 * Description:
 * ============================================================================>
 * ------------------------------版权声明----------------------------
 * 此文件为SuperMap Objects Java 的示范代码 
 * 版权所有：北京超图软件股份有限公司
 * ----------------------------------------------------------------
 * ---------------------SuperMap iObjects Java 示范程序说明------------------------
 * 
 * 1、范例简介：示范如何使用SceneControl进行三维量算
 * 2、示例数据：安装目录\SampleData\City\Changchun.smwu 
 * 3、关键类型/成员: 
 * 		MapControl.getMap 方法
 * 		MapControl.setWaitCursorEnabled	方法
 *      Map.outputMapToBMP 方法
 *      Map.outputMapToJPG 方法
 *      Map.outputMapToPNG 方法 
 * 4、使用步骤：
 *   (1)点击相应的出图按钮出各种格式的图片
 *   (2)出PNG图片根据提示选择出的图片是否背景透明
 * ------------------------------------------------------------------------------
 * ============================================================================>
 * </p> 
 * 
 * <p>
 * Company: 北京超图软件股份有限公司
 * </p>
 * 
 */

public class SampleRun {

	private Workspace m_workspace;

	private MapControl m_mapControl;

	/**
	 * 根据workspace和mapControl构造 SampleRun对象
	 */
	public SampleRun(Workspace workspace, MapControl mapControl) {
		m_workspace = workspace;
		m_mapControl = mapControl;

		m_mapControl.getMap().setWorkspace(workspace);
		initialize();
	}

	/**
	 * 打开需要的工作空间文件及地图
	 */
	private void initialize() {
		try {
			// 打开工作空间及地图
//			WorkspaceConnectionInfo conInfo = new WorkspaceConnectionInfo(
//					"../../SampleData/City/Changchun.smwu");
			WorkspaceConnectionInfo conInfo = new WorkspaceConnectionInfo(
					"G:\\supermap\\软件安装\\SMO_Java_811_14428_59859_Win_vc11_CHS_Zip\\SampleData\\City\\Changchun.smwu");
			conInfo.setType(WorkspaceType.SMWU);
			m_workspace.open(conInfo);
			this.m_mapControl.getMap().open(m_workspace.getMaps().get(0));

			// 调整mapControl的状态
			m_mapControl.setWaitCursorEnabled(false);
			m_mapControl.getMap().viewEntire();
			m_mapControl.getMap().refresh();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 根据文件后缀名选择要保存的文件路径和格式，并返回选择的路径
	 */
	private String getFilePath(String postfix) {
		String path = null;

		try {
			//设置文件过滤器
//			JFileChooser chooser = new JFileChooser("../../SampleData");
			JFileChooser chooser = new JFileChooser("G:\\\\supermap\\\\软件安装\\\\SMO_Java_811_14428_59859_Win_vc11_CHS_Zip\\\\SampleData");
			chooser.setName("请选择保存路径");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(String.format("%1$s格式(*.%1$s)", postfix), postfix);
			chooser.setFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false);
			int returnValue = chooser.showSaveDialog(null);

			//保存文件
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String fileName = chooser.getSelectedFile().getName();
				if (fileName.contains(postfix)) {
					path = chooser.getSelectedFile().getPath();
				} else {
					path = String.format("%1$s.%2$s", chooser.getSelectedFile()
							.getPath(), postfix);
				}
				//判断文件是否存在
				File file = new File(path);
				if (file.exists()) {
					int value = JOptionPane
							.showConfirmDialog(null, file.getName()
									+ "已经存在,确定覆盖？", "确认框",
									JOptionPane.OK_CANCEL_OPTION);
					if (value == JOptionPane.CANCEL_OPTION) {
						return null;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return path;
	}

	/**
	 * 出BMP图
	 */
	public void outToBMP() {
		try {
			String path = getFilePath("bmp");
			if (path != null && m_mapControl.getMap().outputMapToBMP(path)) {
				JOptionPane.showMessageDialog(null, "出图成功");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 出PNG图
	 */
	public void outToPNG() {
		try {
			boolean isBackTransparent = false;
			if ((JOptionPane.showOptionDialog(null, "是否背景透明", "是否背景透明",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, null, null)) == JOptionPane.YES_OPTION) {
				isBackTransparent = true;
			}
			String path = getFilePath("png");
			if (path != null
					&& m_mapControl.getMap().outputMapToPNG(path,
							isBackTransparent)) {
				JOptionPane.showMessageDialog(null, "出图成功");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 出JPG图
	 */
	public void outToJPG() {
		try {
			String path = getFilePath("jpg");
			if (path != null && m_mapControl.getMap().outputMapToJPG(path)) {
				JOptionPane.showMessageDialog(null, "出图成功");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
/**
 * 内部类，实现文件选择对话框 
 */
class FileNameExtensionFilter extends FileFilter{
	private String str = "";
	private ArrayList<String> m_extensions = new ArrayList<String>();
	
	public FileNameExtensionFilter(String description, String... extensions) {
		this.setDescription(description);
		for (String extension : extensions) {
			this.addExtension(extension);
		}
	}
	
	public void addExtension(String extension) {
		if (!extension.startsWith(".")) {
			extension = "." + extension;
		}
		m_extensions.add(extension.toLowerCase());
	}
	
	public String[] getExtensions() {
		String[] result = new String[m_extensions.size()];
		return m_extensions.toArray(result);
	}
	
	public void setDescription(String aDescription) {
		str = aDescription;
	}
	
	public String getDescription() {
		return str;
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String name = f.getName().toLowerCase();
		for (String extension: m_extensions) {
			if (name.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}

