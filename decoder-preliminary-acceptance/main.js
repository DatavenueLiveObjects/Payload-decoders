/* eslint-disable */
const fs = require('fs');
const { program } = require('commander');
const { ESLint } = require('eslint');
const path = require('path');

const buffer = fs.readFileSync("help.js");
const helpData = buffer.toString();


program
	.requiredOption('-f, --file <filename>', 'Specify the file to read')
	.option('-o, --outputFile <filename>', 'Specify the file to write an output script there')
	.option('-l, --payload <string>', 'Specify a payload to test the decoder on')
	.option('-h, --help', 'Display the content of the file help.js');
program.showHelpAfterError(helpData);
program.parse();



const file = program.opts().file;
let isDecoderValid = true;
if (program.opts().payload)//optional payload
{
	payload = program.opts().payload;
}


if (program.opts().help ) {
	console.log(helpData)
}

//running lint on decoder file
async function lint() {
	const configFile =path.join(process.cwd(), '.eslintrc.js');
	const eslint = new ESLint({
		overrideConfigFile: configFile,
	});
	// Lint files
	const results = await eslint.lintFiles([file]);
	// Print errors
	const formatter = await eslint.loadFormatter('stylish');
	const resultText = formatter.format(results);
	console.log(resultText);
	if(resultText){
		isDecoderValid = false;
	}
}
lint().catch((error) => {
	isDecoderValid=false;
	console.error('Error running ESLint:', error);
});


function hasTryCatchInDecode(code) {
	//regex matching function named decode and containing try...catch
	let decodeWithTryCatchRegex = /decode\s*=\s*[^;]*{\s*?try\s*\s*{[\s\S]*?}\s*catch\s*\([^)]*\)\s*{[\s\S]*?}\s*/;
	let decodeAndSplitWithTryCatchRegex = /decodeAndSplit\s*=\s*[^;]*{\s*?try\s*\s*{[\s\S]*?}\s*catch\s*\([^)]*\)\s*{[\s\S]*?}\s*/;
	return (decodeWithTryCatchRegex.test(code)|| decodeAndSplitWithTryCatchRegex.test(code));
}


function codeParse(content)
{
	try {
		eval(content)
		let value
		if (typeof decode === 'function') {
			value = decode(payload);
		} else if (typeof decodeAndSplit() === 'function') {
			value = decodeAndSplit(payload)
		} else {
			console.error('no necessary function declared, no decode or decodeAndSplit');
		}
		const decodeReturnType = typeof (value);
		if (decodeReturnType === typeof (JSON.stringify({x: 5, y: 6}))) {
		} else {
			isDecoderValid = false;
			console.error("decode function doesnt return right type of object, is", decodeReturnType, "should be string")
		}
		let parsedValue = JSON.parse(value);
		if (typeof (parsedValue) === 'object' && !Array.isArray(parsedValue)) {
		} else {
			isDecoderValid = false;
			console.error('value returned by decode function is not a stringified JSON object');
		}
	}
	catch (error){
		if (error.name == 'ReferenceError') console.log('Reference error => didn\'t check the returned type, assumed the decoder is valid.')
		else console.error(error)
	}
}

const fileContent = fs.readFileSync(file, 'utf8');
if(program.opts().payload){
	codeParse(fileContent);
}

fs.readFile(file, 'utf8', (error, data) => {
	if (error) {
		console.error(`Error reading file: ${error}`);
		return;
	}

	//replacing //comments with /*comments*/
	const regex = /\/\/(.*?)(?=[\r\n]|$)/g;
	let result = data.replace(regex, '/*$1*/\n')

	if(!hasTryCatchInDecode(result))
	{
		console.error('\x1b[31m%s\x1b[0m', 'error: code in decoding function isn\'t in try..catch')
		isDecoderValid=false;
	}

	// Write the modified content back to the file
	if (program.opts().outputFile && isDecoderValid) {
		result = result.replace(/(\r\n|\r|\n|\t)/g,'');
		result = result.replace(/(\\(?=[^rnt'"]))/g,"\\\\");
		result = result.replace(/(\\")/g,"'");
		result = result.replace(/(")/g,"\\\"");

		fs.writeFile(program.opts().outputFile, result, 'utf8', (error) => {
			if (error) {
				console.error(`Error writing file: ${error}`);
				return;
			}
			console.log('File processed successfully!');
		});
	}
});
