const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const [r, c] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < r; i++) {
  board.push(input[1 + i].trim());
}
const delta = [[-1, 0], [1, 0], [0, 1], [0, -1]];

const visited = {};
let allStop = false;

console.log(recur());



function recur(x = 0, y = 0) {
  if (allStop) { return 0; }
  if (visited[board[x][y]]) { return 0; }

  visited[board[x][y]] = true;

  let ret = 0;
  for (let [dx, dy] of delta) {
    let [nx, ny] = [x + dx, y + dy];
    if (0 <= nx && nx < r && 0 <= ny && ny < c && !visited[board[nx][ny]]) {
      ret = Math.max(ret, recur(nx, ny));
      if (ret === 25) {
        allStop = true;
        return 26;
      }
    }
  }
  visited[board[x][y]] = false;

  return 1 + ret;
}