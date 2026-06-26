const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const delta = [
  [-1, 0],
  [1, 0],
  [0, -1],
  [0, 1],
];
const sameRoad = [0, 0]; // [size]

const [n, m] = input[0].split(" ").map(Number);
const board = [];
const walls = [];
for (let i = 0; i < n; i++) {
  // 입력력
  board.push(input[1 + i].split("").map(Number));
  // 벽 위치 파악
  for (let j = 0; j < m; j++) {
    if (board[i][j] === 1) {
      walls.push([i, j]);
    }
  }
}

for (let i = 0; i < n; i++) {
  for (let j = 0; j < m; j++) {
    if (board[i][j] !== 0) continue;
    const roadNumber = sameRoad.length;
    const queue = [[i, j]];
    board[i][j] = roadNumber;
    let cnt = 1;

    while (queue.length > 0) {
      let [x, y] = queue.shift();
      for (let [dx, dy] of delta) {
        let [nx, ny] = [x + dx, y + dy];
        if (nx < 0 || nx >= n) continue;
        if (ny < 0 || ny >= m) continue;
        if (board[nx][ny] !== 0) continue;
        cnt++;
        board[nx][ny] = roadNumber;
        queue.push([nx, ny]);
      }
    }
    sameRoad.push(cnt);
  }
}

let temp = [...Array(n)].map(() => Array(m).fill(0));
for (let [i, j] of walls) {
  let cnt = 1;
  const check = {};
  for (let [dx, dy] of delta) {
    let [nx, ny] = [i + dx, j + dy];
    if (nx < 0 || nx >= n) continue;
    if (ny < 0 || ny >= m) continue;
    if (board[nx][ny] === 1) continue;
    const roadNumber = board[nx][ny];
    if (check[roadNumber] === undefined) {
      check[roadNumber] = true;
      cnt += sameRoad[roadNumber];
    }
  }
  temp[i][j] = cnt % 10;
}
let answer = temp.map((arr) => arr.join("")).join("\n");
console.log(answer);
