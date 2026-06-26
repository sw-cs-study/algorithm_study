const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

let answer = "";
for (const line of input) {
  if (line.length === 1) break;
}
