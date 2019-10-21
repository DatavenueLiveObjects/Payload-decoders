/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

var decode = function(encoded) {

	try {
		/* <enter your javascript code here */
		/* return the json output */
		return JSON.stringify({
			"field1" : "1"
		});
	} catch (e) {
		return "{\"error\":\"decoding failed\"}";
	}
};
