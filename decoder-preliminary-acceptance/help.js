/**********************************************
*This script checks the validity of decoder
*
*Usage:
*node main.js [options]
*
*Options:
*  -f, --file <filename>       Specifies the input file with decoder.
*  -o, --output <filename>     (Optional) Specifies the output file to write the valid one line script to
*  -l, --payload <payload_str> (Optional) Specifies the payload string that is used as an input for decoder
*  -h, --help                  Displays this help message.
*
*Examples:
*1. Check if the decoder is valid without payload and without saving output one-line script:
*   node main.js -f myDecoderV1.0.js
*
*2. Check if the decoder works correct after being fed payload and write one-line script to output.txt
*   node main.js -f input.txt -o output.txt -l "011D5A78FA2C01F080"
*
*3. Display help message:
*   node main.js -h
*****************************************/
