const input = require("fs")
  .readFileSync(
    process.platform === "linux" ? "/dev/stdin" : "../../sample.txt"
  )
  .toString()
  .trim()
  .split("\n");

const [R, C, M] = input[0].split(" ").map(Number);
const queue = [];
let board = [...Array(R)].map(() => Array(C).fill(0)); // 사이즈로 해당 칸에 존재하는지를 판단 + 이전껀지 판단

let closestRow = R;
let sizeSum = 0;
for (let i = 0; i < M; i++) {
  let [r, c, s, d, z] = input[1 + i].split(" ").map(Number);
  queue.push([r - 1, c - 1, s, d, z]);
  board[r - 1][c - 1] = z;
  if (c - 1 === 0 && r - 1 < closestRow) {
    closestRow = r - 1;
  }
}

for (let i = 0; i < C; i++) {
  // 이동은 i가 알아서함
  // 가까운 상어 잡기
  if (closestRow !== R) {
    sizeSum += board[closestRow][i];
    board[closestRow][i] = 0;
  }
  // 상어 이동시키기 + 잡아먹기
  closestRow = R;
  let size = queue.length;
  let temp = [...Array(R)].map(() => Array(C).fill(0));
  for (let k = 0; k < size; k++) {
    let [r, c, s, d, z] = queue.shift();
    // if shark was eaten
    if (board[r][c] !== z) continue;
    let [nr, nc, nd] = convert(r, c, s, d);
    if (i + 1 < C && nc === i + 1 && nr < closestRow) closestRow = nr;
    if (temp[nr][nc] > z) continue;
    temp[nr][nc] = z;
    queue.push([nr, nc, s, nd, z]);
  }
  board = temp;
}
console.log(sizeSum);

function convert(row, col, s, d) {
  if (d === 1) {
    // up
    s %= 2 * (R - 1);
    let v = row - s;
    if (0 <= v) {
      row = v;
    } else if (-R < v && v <= -1) {
      row = -v;
      d = 2;
    } else if (v <= -R) {
      row = R - 2 + (R + v);
    }
  } else if (d === 2) {
    // down
    s %= 2 * (R - 1);
    let v = row + s;
    if (0 <= v && v < R) {
      row = v;
    } else if (R <= v && v < 2 * R - 1) {
      row = R - 2 - (v - R);
      d = 1;
    } else if (2 * R - 1 <= v) {
      row = v - 2 * R + 2;
    }
  } else if (d === 3) {
    // right
    s %= 2 * (C - 1);
    let v = col + s;
    if (0 <= v && v < C) {
      col = v;
    } else if (C <= v && v < 2 * C - 1) {
      col = C - 2 - (v - C);
      d = 4;
    } else if (2 * C - 1 <= v) {
      col = v - 2 * C + 2;
    }
  } else if (d === 4) {
    // left
    s %= 2 * (C - 1);
    let v = col - s;
    if (0 <= v) {
      col = v;
    } else if (-C < v && v <= -1) {
      col = -v;
      d = 3;
    } else if (v <= -C) {
      col = C - 2 + (C + v);
    }
  }

  return [row, col, d];
}
