const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const arr = input[1].split(" ").map(Number);
arr.sort((a, b) => b - a);

let sum = 0;
for (let i = 0; i < n; i++) {
  if (arr[i] - i < 0) break;
  sum += arr[i] - i;
}

console.log(sum);
