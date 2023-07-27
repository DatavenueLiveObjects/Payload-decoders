/* eslint-disable */
module.exports = {
	"env": {
		"browser": true,
		"es2021": true
	},

	globals: {
		// Define "java" as readonly to ignore the "no-undef" rule for it
		java: 'readonly',
		BinaryDecoder: 'readonly',
		Hex2Bin: 'readonly',
		JsSplitUtils: 'readonly',
		JsUtils: 'readonly',
		JsonUtils: 'readonly',
		CsvDecoder: 'readonly',
	},
	"extends": [
		"eslint:recommended",
		"plugin:es5/no-es2015"
	],
	"plugins": [
		"es5",
	],
	"ignorePatterns": ["main.js"],
	"parserOptions": {
		"ecmaVersion": 6,
		"sourceType": "script"
	},
	"rules": {
		"semi": [
			"error",
			"always"
		],
		"no-extra-semi" :"off",
		"no-mixed-spaces-and-tabs": "off",
		"no-unused-vars": [
			'warn',
			{
				varsIgnorePattern: '^(decode|decodeAndSplit)$'
			}
		],

	},
};
