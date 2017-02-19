var base_URL = 'http://52.37.179.16:8181/MilkDistribution/rest';

function prepareRequestData(bodyData, serviceName) {
	var jsonRequest = {};
	var header = {};
	header.serviceName = serviceName;
	jsonRequest.header = header;
	jsonRequest.body = bodyData;
	return JSON.stringify(jsonRequest);
}