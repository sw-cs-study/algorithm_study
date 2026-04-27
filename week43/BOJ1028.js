const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [R, C] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < R; i++) {
  board.push(input[1 + i].split("").map(Number));
}

const checkingLowerDiamond = [...Array(R)].map(() =>
  [...Array(C)].map(() => Array(2).fill(0)),
);
for (let i = 0; i < R; i++) {
  for (let j = 0; j < C; j++) {
    if (board[i][j] === 1) {
      let [leftUpper, rightUpper] = [0, 0];
      if (i > 0 && j > 0) leftUpper = checkingLowerDiamond[i - 1][j - 1][0];
      if (i > 0 && j < C - 1)
        rightUpper = checkingLowerDiamond[i - 1][j + 1][1];

      checkingLowerDiamond[i][j][0] = 1 + leftUpper;
      checkingLowerDiamond[i][j][1] = 1 + rightUpper;
    }
  }
}
const checkingUpperDiamond = [...Array(R)].map(() =>
  [...Array(C)].map(() => Array(2).fill(0)),
);
for (let i = R - 1; i > -1; i--) {
  for (let j = C - 1; j > -1; j--) {
    if (board[i][j] === 1) {
      let [leftLower, rightLower] = [0, 0];
      if (i < R - 1 && j > 0) leftLower = checkingUpperDiamond[i + 1][j - 1][0];
      if (i < R - 1 && j < C - 1)
        rightLower = checkingUpperDiamond[i + 1][j + 1][1];

      checkingUpperDiamond[i][j][0] = 1 + leftLower;
      checkingUpperDiamond[i][j][1] = 1 + rightLower;
    }
  }
}

const countingLowerDiamond = [...Array(R)].map(() => Array(C).fill(0));
for (let i = 0; i < R; i++) {
  for (let j = 0; j < C; j++) {
    let k = Math.min(...checkingLowerDiamond[i][j]);
    countingLowerDiamond[i][j] = k;
  }
}
const countingUpperDiamond = [...Array(R)].map(() => Array(C).fill(0));
for (let i = 0; i < R; i++) {
  for (let j = 0; j < C; j++) {
    let k = Math.min(...checkingUpperDiamond[i][j]);
    countingUpperDiamond[i][j] = k;
  }
}

let answer = 0;
for (let i = 0; i < R; i++) {
  for (let j = 0; j < C; j++) {
    if (countingUpperDiamond[i][j] > 0) {
      let k = countingUpperDiamond[i][j];
      let result = k;
      if (result <= answer) continue;
      for (let v = k; v > 0; v--) {
        let ui = i + 2 * (v - 1);
        if (ui >= R) continue;
        if (countingLowerDiamond[ui][j] >= v) {
          result = v;
          break;
        }
      }
      if (result > answer) answer = result;
    }
  }
}
console.log(answer);
