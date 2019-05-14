package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.MapUtils;
import com.example.common.response.ResponseResult;
import com.example.po.SMDTV;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.DatasetVectorInfo;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.EngineType;
import com.supermap.data.Feature;
import com.supermap.data.Geometrist;
import com.supermap.data.Geometry;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.SpatialQueryMode;
import com.supermap.data.Toolkit;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.vo.RiskClaimVo;

@RestController
@RequestMapping(value="/file")
public class FileUploadController {
	/*静态变量*/
	private static final  String  iobjectJavaServer = "10.10.68.248:1521/orcl";
	private static final  String  iobjectJavaDatabase = "riskcontrol";
	private static final  String  iobjectJavaUser = "riskcontrol";
	private static final  String  iobjectJavaPassword = "riskcontrol";
	private static final  String  riskMap_address  = "SMDTV_60";
	
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
	
	/**
	 * iobjectjava 直接把后台的多个面数据转换成一个面数据
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * */
	@RequestMapping(value="/returnGeometrist",method= {RequestMethod.POST,RequestMethod.GET})
	public ResponseResult  returnGeometrist(@RequestBody RiskClaimVo riskClaimVo) {
		System.out.println(riskClaimVo.getGeometrys());
		ResponseResult responseResult =new ResponseResult();
		
		Workspace workspace = new Workspace();
		// 定义数据源连接信息，假设以下所有数据源设置都存在
	    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
		//进行数据源的连接
		Datasource datasource =MapUtils.connectDataSource(workspace,datasourceconnection,iobjectJavaServer,iobjectJavaDatabase,iobjectJavaUser,iobjectJavaPassword);
		// 获取的面数据集
//		TF_7M
	    DatasetVector datasetVector_7 = (DatasetVector)datasource.getDatasets().get("TF_7M");
	    
	    String filter = "TFBH = 201822";
	    Recordset recordset_7 = datasetVector_7.query(filter,CursorType.DYNAMIC ); 
	    Map<Integer,Feature>  features= recordset_7.getAllFeatures();
		String geometryString = "";
		List<Geometry> geoList=new ArrayList<Geometry>();
	    if(recordset_7.getRecordCount()>0){
	    	for(Feature feature:features.values()){
	    		Geometry geometry= feature.getGeometry(); 
	    		geoList.add(geometry);
			}
	    	
	    	Geometry geometry = geoList.get(0);
	    	for (int j = 1;j<geoList.size();j++) {
    			Geometry geome= geoList.get(j);
    			geometry = Geometrist.union(geometry, geome);
    		}
	    	geometryString = Toolkit.GemetryToGeoJson(geometry);
	    	
	    }else {
	    	System.out.println("==============没有数据");
	    }
	    responseResult.setResult(geometryString);
        
        
        if(recordset_7!=null){
        	recordset_7.close();
        	recordset_7.dispose();
		}
//	    if(fieldInfoNew!=null){
//	    	fieldInfoNew.dispose();
//		}
		if(datasetVector_7!=null){
			datasetVector_7.close();
		}
	    if(datasource!=null){
			datasource.close();
		}
	    if(datasourceconnection!=null){
			datasourceconnection.dispose();
		}
		if(workspace!=null){
			// 关闭工作空间
			workspace.close();
			// 释放该对象所占用的资源
			workspace.dispose();
		}
		
		return responseResult;
	}
	
	@RequestMapping(value="/operatePointInfo",method= {RequestMethod.POST,RequestMethod.GET})
	public ResponseResult  operatePointInfo() {
		
		ResponseResult responseResult =new ResponseResult();
		
		Workspace workspace = new Workspace();
		// 定义数据源连接信息，假设以下所有数据源设置都存在
	    DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
		//进行数据源的连接
		Datasource datasource =MapUtils.connectDataSource(workspace,datasourceconnection,iobjectJavaServer,iobjectJavaDatabase,iobjectJavaUser,iobjectJavaPassword);
		// 获取的面数据集
//		TF_7M
	    DatasetVector datasetVector_7 = (DatasetVector)datasource.getDatasets().get("UnionTF_7_10");
	    // 获取的点数据集
	    DatasetVector datasetVector = (DatasetVector)datasource.getDatasets().get("RISKMAP_ADDRESS");
	    
//	    DatasetVector datasetVector = (DatasetVector)datasource.getDatasets().get("BB_1");
	    int count =  datasetVector.getRecordCount();
	    System.out.println("RISKMAP_ADDRESS总数为：" + count);
	    
	    QueryParameter parameter = new QueryParameter();
		parameter.setSpatialQueryObject(datasetVector_7);
//		parameter.setAttributeFilter("SmID<100000");
		parameter.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
		
		Recordset queryRecordset = datasetVector.query(parameter);
		System.out.println("相交之后的点的数量："+queryRecordset.getRecordCount());
		
		List<SMDTV> smdtvList = new ArrayList<SMDTV>();
		int i=0;
		while (!queryRecordset.isEOF()) {
			i++;
			SMDTV smdtv =new SMDTV();
			Object  SMID  = queryRecordset.getFieldValue("SMID");
			Object  POINTX_2000  = queryRecordset.getFieldValue("POINTX_2000");
			Object  POINTY_2000  = queryRecordset.getFieldValue("POINTY_2000");
			
//			MapUtils.queryCorporePInfo(SMID,POINTX_2000,POINTY_2000);
			smdtv.setPointx_2000(POINTX_2000.toString());
			smdtv.setPointy_2000(POINTY_2000.toString());
			smdtvList.add(smdtv);
//	        recordset.setValues(values);
			queryRecordset.moveNext();
			System.out.println("=============循环次数=============:"+i);
	    }
		responseResult.setResult(smdtvList);
		responseResult.setStatusCode("1");
		
//		Map<Integer,Feature>  features= queryRecordset.getAllFeatures();
//		List<SMDTV> smdtvList = new ArrayList<SMDTV>();
//		if(queryRecordset.getRecordCount()>0){
//			for(Feature feature:features.values()){
//				SMDTV smdtv =new SMDTV();
//				String  POINTX_2000 = feature.getString("POINTX_2000");
//				String  POINTY_2000 = feature.getString("POINTY_2000");
//				smdtv.setPointx_2000(POINTX_2000);
//				smdtv.setPointy_2000(POINTY_2000);
//				smdtvList.add(smdtv);
//			}
//			responseResult.setResult(smdtvList);
//			responseResult.setStatusCode("1");
//		}else {
//			System.out.println("================没有数据");
//		}
		
		System.out.println("================一切都结束了");
		
		if(parameter!=null){
			parameter.dispose();
		}
		
	    if(datasetVector!=null){
	    	datasetVector.close();
		}
	    
//	    if(fieldInfoNew!=null){
//	    	fieldInfoNew.dispose();
//		}
		if(datasetVector_7!=null){
			datasetVector_7.close();
		}
	    if(datasource!=null){
			datasource.close();
		}
	    if(datasourceconnection!=null){
			datasourceconnection.dispose();
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
