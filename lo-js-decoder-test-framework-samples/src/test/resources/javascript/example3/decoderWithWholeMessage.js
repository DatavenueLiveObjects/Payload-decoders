/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

var decode = function(encoded, dataMessage) {
	try {

		var dataMessage = JSON.parse(dataMessage);

		var decoded;

		switch (dataMessage.metadata.network.lora.port) {
		case 1:
			decoded = "decodedPort1";
			break;
		case 2:
			decoded = "decodedPort2";
			break;
		case 3:
			decoded = "decodedPort3";
			break;
		default:
			decoded = "decodedPort";
		}

		return JSON.stringify({
			"field1" : decoded
		});
	} catch (e) {
		return "{\"error\":\"decoding failed\"}";
	}
};
