const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < n; i++) {
  board.push(input[1 + i]);
}

const delta = [
  [-1, 0],
  [0, 1],
  [1, 0],
  [0, -1],
];

let count = 0;
for (let i = 0; i < n; i++) {
  for (let j = 0; j < m; j++) {
    let flag = true;
    for (let [dx, dy] of delta) {
      let [nx, ny] = [i + dx, j + dy];
      if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
      if (board[i][j] !== board[nx][ny]) {
        flag = false;
        break;
      }
    }
    if (flag) {
      count++;
    }
  }
}

console.log(customPow(count));

function customPow(x) {
  if (x === 0) return 0;

  const MOD = 1000000007;
  let ret = 1;
  for (let i = 0; i < count; i++) {
    ret = (ret * 2) % MOD;
  }
  return ret;
}
