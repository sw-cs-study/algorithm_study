const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

let [n, m] = input[0].split(" ").map(Number);
let first = [];
let second = [];
for (let i = 0; i < n; i++) {
  first = input[1].split(" ").map(Number);
}
first.sort((a, b) => a - b);
for (let i = 0; i < m; i++) {
  second = input[2].split(" ").map(Number);
}
second.sort((a, b) => a - b);
if (n > m) {
  // second 가 더 많음
  [first, second] = [second, first];
  [n, m] = [m, n];
}

const dp = [...Array(n)].map(() => Array(m).fill(Infinity));

for (let firstIdx = 0; firstIdx < n; firstIdx++) {
  for (let secondIdx = firstIdx; secondIdx < m; secondIdx++) {
    let value = Math.abs(first[firstIdx] - second[secondIdx]);

    if (firstIdx === 0) {
      if (secondIdx > 0) {
        value = Math.min(value, dp[firstIdx][secondIdx - 1]);
      }
      dp[firstIdx][secondIdx] = value;
      continue;
    }

    dp[firstIdx][secondIdx] = Math.min(
      dp[firstIdx - 1][secondIdx - 1] + value,
      dp[firstIdx][secondIdx - 1]
    );
  }
}
console.log(dp[n - 1][m - 1]);
