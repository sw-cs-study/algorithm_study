const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const weights = input[1].split(" ").map(Number);
weights.sort((a, b) => a - b);
let max = 0;
for (let w of weights) {
  if (max + 1 < w) {
    break;
  } else {
    max += w;
  }
}
console.log(max + 1);
