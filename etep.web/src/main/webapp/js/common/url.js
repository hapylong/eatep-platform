/**
 * 后台访问url
 */
var req_ip = 'http://localhost:8080';//本地
//var req_ip = 'http://101.201.151.38:8088';//consumer开发
//var req_ip = 'https://asset-test.kehujiabj.com:443';//etep测试
//var req_ip = 'http://112.126.81.154:8088';//consumer测试
//var req_ip = 'https://114.55.179.195:443';//consumer测试https
//var req_ip = 'http://114.215.252.24:8088';//consumer生产
//var project_name = '/etep.front';
var rabbit_ip = 'http://118.178.243.45:15674/stomp';

var project_name = '/asset.manage.front';
var urls= {
	
	rootUrl: req_ip + project_name,
	baseUrl: req_ip,
	webUrl: req_ip + '/asset.web',	
	imgUrl: req_ip + '/upload_data',
	sysmanegeUrl: req_ip + project_name,
	workflowUrl: req_ip + project_name,
	cfm: req_ip + project_name,
	cmf: req_ip + project_name,
	house:req_ip + project_name,
	rabbit:rabbit_ip,
	rabbitEnv:'kehujia_test'
}
