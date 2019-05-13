package test;

import java.util.Map;

import com.example.common.response.ResponseResult;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EngineType;
import com.supermap.data.Feature;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.SpatialQueryMode;
import com.supermap.data.Workspace;

public class PointVectorCross {
	private static final String iobjectJavaServer="localhost:3306/shop";
	private static final String iobjectJavaDatabase="shop";
	private static final String iobjectJavaUser="root";
	private static final String iobjectJavaPassword="qk941009";
	
    public  static void main(String []args) {
    	operatePointInfo();
    }
    
    public static ResponseResult  operatePointInfo(){
    	
    	ResponseResult responseResult =new ResponseResult();
    	
    	Workspace workspace = new Workspace();
    	// 定义数据源连接信息，假设以下所有数据源设置都存在
        DatasourceConnectionInfo datasourceconnection = new  DatasourceConnectionInfo();
        
    	//进行数据源的连接
//    	Datasource datasource =MapUtils.connectDataSource(workspace,datasourceconnection,iobjectJavaServer,iobjectJavaDatabase,iobjectJavaUser,iobjectJavaPassword);
    	 datasourceconnection.setEngineType(EngineType.MYSQL);
         datasourceconnection.setServer(iobjectJavaServer);
         datasourceconnection.setDatabase(iobjectJavaDatabase);
         datasourceconnection.setUser(iobjectJavaUser); // riskcontrol_freeze
         datasourceconnection.setPassword(iobjectJavaPassword);
    	 datasourceconnection.setAlias("MYSQL");
        // 打开数据源
        Datasource datasource = workspace.getDatasources().open(datasourceconnection);
        
        // 获取的点数据集
//    	  DatasetVector datasetVector = (DatasetVector)datasource.getDatasets().get("RISKMAP_ADDRESS");
        
        if (datasource == null) {
            System.out.println("打开数据源失败");
        } else {
           System.out.println("数据源打开成功！");
        }
        
    	// 获取的面数据集
//    	TF_7M
        DatasetVector datasetVector_7 = (DatasetVector)datasource.getDatasets().get("circleName");
        
        // 获取的点数据集
        DatasetVector datasetVector = (DatasetVector)datasource.getDatasets().get("address_1");
        
        QueryParameter parameter = new QueryParameter();
    	parameter.setSpatialQueryObject(datasetVector_7);
    	parameter.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
    	
    	Recordset queryRecordset = datasetVector.query(parameter);
    	Map<Integer,Feature>  fetures = queryRecordset.getAllFeatures();
    	
    	if(queryRecordset.getRecordCount()>0) {
    		for(Feature feature : fetures.values()) {
    			String SmID = feature.getString("SmID");
    			System.out.println("===========:"+SmID);
    			String SMX = feature.getString("SMX");
    			String SMY = feature.getString("Y");
    			System.out.println("===="+SMX+":"+SMY);
    		}
    	}else {
    		System.out.println("==================没有数据");
    	}
    	
    	if(parameter!=null){
    		parameter.dispose();
    	}
    	
        if(datasetVector!=null){
        	datasetVector.close();
    	}
        
//        if(fieldInfoNew!=null){
//        	fieldInfoNew.dispose();
//    	}
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

