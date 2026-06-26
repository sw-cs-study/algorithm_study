const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const tc = Number(input[0]);
for (let t = 0; t < tc; t++) {
  const [n, m] = input;
}
