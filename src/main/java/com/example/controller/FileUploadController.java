package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.common.response.ResponseResult;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.DatasetVectorInfo;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.EncodeType;
import com.supermap.data.EngineType;
import com.supermap.data.Geometrist;
import com.supermap.data.Geometry;
import com.supermap.data.Recordset;
import com.supermap.data.Toolkit;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.vo.RiskClaimVo;

@RestController
@RequestMapping(value="/file")
public class FileUploadController {
	/**
	 * 处理文件上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * */
	@RequestMapping(value="/fileUpload",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> fileUpload(@RequestParam(value="file") MultipartFile file) throws IllegalStateException, IOException{
		System.out.println(file.getOriginalFilename());
		String path = "E:/"+file.getOriginalFilename();
		File fileNew =new File(path);
		file.transferTo(fileNew);
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("msg", "ok");
		return map;
		
	}
	
	/**
	 * iobjectjava 通过前台传过来的geometry数据转换成面数据
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * */
	@RequestMapping(value="/fileGeometrist",method= {RequestMethod.POST,RequestMethod.GET})
	public ResponseResult  fileGeometrist(@RequestBody RiskClaimVo riskClaimVo) {
		System.out.println(riskClaimVo.getGeometrys());
		ResponseResult responseResult =new ResponseResult();
		Workspace workspace = new Workspace();
		WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();
		Datasource datasource = new Datasource(EngineType.UDB);
		workspaceConnectionInfo.setType(WorkspaceType.SMWU);

//		String  file = "F:/A_supermap/superMap_file/Dissovle/dissolveDatasetVector/data/dissovle.smwu";
//		String  file = "F:/A_supermap/superMap_file/Dissovle/data/dissovle.smwu";
		String  file = "C:/Users/Administrator/Desktop/data/dissovle.smwu";
		
//		String file =filePath.getString("filePath");
		workspaceConnectionInfo.setServer(file);
		workspace.open(workspaceConnectionInfo); 
		datasource = workspace.getDatasources().get("dissovle"); 
		
		if (datasource == null) {
            System.out.println("打开数据源失败");
	    } else {
	        System.out.println("数据源打开成功！");
	    }
		Datasets datasets = datasource.getDatasets();
		 String name = datasets.getAvailableDatasetName("河流1");
		// 设置矢量数据集的信息
        DatasetVectorInfo datasetVectorInfo = new DatasetVectorInfo();
        datasetVectorInfo.setName(name);
        datasetVectorInfo.setType(DatasetType.REGION );
        datasetVectorInfo.setEncodeType(EncodeType.NONE);
        datasetVectorInfo.setFileCache(true);
        
        System.out.println("矢量数据集的信息为：" + datasetVectorInfo.toString());

        // 创建矢量数据集
        DatasetVector datasetVector = datasets.create(datasetVectorInfo);
        Recordset recordset = datasetVector.getRecordset(false, CursorType.DYNAMIC);
        
//		// ThiessenPolygon
//		DatasetVector dtv=(DatasetVector) datasource.getDatasets().get("New1_ThiessenPolygon_1");
//		Recordset recordset = dtv.getRecordset(false, CursorType.DYNAMIC);
		
//		Geometry geome1= Toolkit.GeoJsonToGemetry(riskClaimVo.getGeometrys()[0]); 
//		Geometry geome2= Toolkit.GeoJsonToGemetry(riskClaimVo.getGeometrys()[1]); 
//		
//		Geometry geometry = Geometrist.union(geome1, geome2);
		
		
		// 锁定当前记录位置
        recordset.edit();
        recordset.update();
        String geometryString = "";
        if(riskClaimVo.getGeometrys().length>0) {
        	Geometry geome = Toolkit.GeoJsonToGemetry(riskClaimVo.getGeometrys()[0]); 
        	if(riskClaimVo.getGeometrys().length==1) {
        		recordset.addNew(geome);
        	}else {
        		for (int i=1;i<riskClaimVo.getGeometrys().length;i++) {
        			Geometry geome1= Toolkit.GeoJsonToGemetry(riskClaimVo.getGeometrys()[i]); 
        			geome = Geometrist.union(geome, geome1);
        		}
        	}
        	recordset.addNew(geome);
        	geometryString = Toolkit.GemetryToGeoJson(geome);
        }
        responseResult.setResult(geometryString);
        
//        recordset.addNew(geometry);
        recordset.update();
        
        recordset.close();
//        geometry.dispose();
        recordset.dispose();
        datasetVector.close();
        if(datasource!=null){
			datasource.close();
		}
	    if(workspaceConnectionInfo!=null){
	    	workspaceConnectionInfo.dispose();
		}
		if(workspace!=null){
			// 关闭工作空间
			workspace.close();
			// 释放该对象所占用的资源
			workspace.dispose();
		}
		
		return responseResult;
	}
	
	
	
}
