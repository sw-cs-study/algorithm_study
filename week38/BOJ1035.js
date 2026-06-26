const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const board = [];
for (let i = 0; i < 5; i++) {
  board.push(input[i].split(""));
}
const filledState = "*";
const emptyState = ".";

const pieces = [];
for (let i = 0; i < 5; i++) {
  for (let j = 0; j < 5; j++) {
    if (board[i][j] === filledState) {
      pieces.push([i, j]);
    }
  }
}

const n = pieces.length;
console.log(getCombination());

function getCombination(number = 0, depth = n) {
  if (number === 25) return null;
  if (depth === 0) return [];

  let ret = [];
  for (let i = number; i < 25 - depth + 1; i++) {
    // 현재 위치 선택
    let result = getCombination(i + 1, depth - 1);
    if (result === null) continue;
    for (let arr of result) {
      ret.push([i, ...arr]);
    }
  }

  return ret;
}
