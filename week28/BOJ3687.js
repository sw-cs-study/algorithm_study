/**
  1: "2",
  2: "5",
  3: "5",
  4: "4",
  5: "5",
  6: "6",
  7: "3",
  8: "7",
  9: "6",
  0: "6",

  2-7개까지 있음
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const tc = Number(input[0]);
let answer = "";

// 최솟값은 dp로
// 여긴 자릿수마다 만들어서 최소로 만드는 거 비교해봐야함. (두 자릿수를 만들려고 할때, 첫째 자리수에 몇개를 쓰고 남은 거로 가능한지도 체크해야하기 때문)
const minNumberTable = [0, 0, 1, 7, 4, 2, 0, 8];
const dp = Array(101).fill(Infinity);
for (let i = 2; i <= 7; i++) {
  if (i === 6) {
    dp[i] = 6; // exception
    continue;
  }
  dp[i] = minNumberTable[i];
}
for (let i = 8; i <= 100; i++) {
  for (let j = 2; j <= 7; j++) {
    dp[i] = Math.min(
      dp[i],
      minNumberTable[j] != 0
        ? dp[i - j] + minNumberTable[j] * Math.pow(10, getDigit(dp[i - j]))
        : Infinity,
      dp[i - j] * 10 + minNumberTable[j]
    );
  }
}

for (let t = 1; t < tc + 1; t++) {
  const n = Number(input[t]);
  answer += solve(n) + "\n";
}
console.log(answer);

function solve(number) {
  return dp[number] + " " + getBiggestValue(number);
}

function getDigit(number) {
  let ret = 1;
  while (Math.floor(number / Math.pow(10, ret)) > 0) {
    ret++;
  }
  return ret;
}

function getBiggestValue(number) {
  if (number % 2 === 0) {
    // 1로 자릿수를 최대한 올리는 게 최대
    return "1".repeat(number / 2);
  } else {
    // 첫자리만 3으로 하고 나머지 자릿수는 1로 최대한 채우는 게 최대
    return "7" + (number - 3 > 0 ? "1".repeat((number - 3) / 2) : "");
  }
}
