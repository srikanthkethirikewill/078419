var base_URL = 'http://localhost:8080/MilkDistribution/rest';

function prepareRequestData(bodyData, serviceName) {
	var jsonRequest = {};
	var header = {};
	header.serviceName = serviceName;
	jsonRequest.header = header;
	jsonRequest.body = bodyData;
	return JSON.stringify(jsonRequest);
}