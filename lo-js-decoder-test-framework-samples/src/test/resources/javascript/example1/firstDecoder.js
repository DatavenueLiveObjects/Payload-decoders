/**
 * 
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
