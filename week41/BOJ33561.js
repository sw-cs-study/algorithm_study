const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

/**
 * 전체 로직은 특정점을 오른쪽 아래 꼭지점으로 가지는 정사각형 중에 가장 가치가 큰 정사각형의 넓이 구하기
 * => 음수가 없으므로 설계도 갯수보다 적게 0을 가지는 정사각형 중 가장 큰 정사각형의 넓이들을 비교하면 된다.
 *
 * 정사각형의 넓이를 구해야하고 해당 정사각형안에 0의 갯수까지 파악해야함(설계도 갯수보다 많은지)
 * 그래서 (0,0) 부터 (x,y) 지점까지로 이루어진 직사각형의 넓이를 저장해서 사용하기
 * 0의 갯수는 위와 똑같은 넓이에서 0의 갯수로 판단하기
 */

const N = Number(input[0]);
const board = [];
for (let i = 0; i < N; i++) {
  board.push(input[1 + i].split(" ").map(Number));
}
const K = Number(input[1 + N]);
const bluePrints = input[2 + N].split(" ").map(Number);
bluePrints.sort((a, b) => b - a);
for (let i = 1; i < K; i++) {
  bluePrints[i] += bluePrints[i - 1];
}

const areaBoard = [...Array(N)].map(() => Array(N).fill(0));
areaBoard[0][0] = board[0][0];
for (let i = 1; i < N; i++) {
  areaBoard[i][0] = board[i][0] + areaBoard[i - 1][0];
}
for (let i = 0; i < N; i++) {
  if (i === 0) {
    for (let j = 1; j < N; j++) {
      areaBoard[i][j] = board[i][j] + areaBoard[i][j - 1];
    }
    continue;
  }

  for (let j = 1; j < N; j++) {
    areaBoard[i][j] =
      board[i][j] +
      areaBoard[i][j - 1] +
      areaBoard[i - 1][j] -
      areaBoard[i - 1][j - 1];
  }
}

const zeroBoard = [...Array(N)].map(() => Array(N).fill(0));
if (board[0][0] === 0) zeroBoard[0][0]++;
for (let i = 1; i < N; i++) {
  zeroBoard[i][0] = zeroBoard[i - 1][0];
  if (board[i][0] === 0) zeroBoard[i][0]++;
}
for (let i = 0; i < N; i++) {
  if (i === 0) {
    for (let j = 1; j < N; j++) {
      zeroBoard[i][j] = zeroBoard[i][j - 1];
      if (board[i][j] === 0) zeroBoard[i][j]++;
    }
    continue;
  }

  for (let j = 1; j < N; j++) {
    zeroBoard[i][j] =
      zeroBoard[i][j - 1] + zeroBoard[i - 1][j] - zeroBoard[i - 1][j - 1];
    if (board[i][j] === 0) zeroBoard[i][j]++;
  }
}

let answer = 0;
for (let i = 0; i < N; i++) {
  for (let j = 0; j < N; j++) {
    let result = findMaxSquareArea(i, j);
    if (result > answer) answer = result;
  }
}
console.log(answer);

function findMaxSquareArea(x, y) {
  let width = Math.min(x, y) + 1;

  // 0 갯수 구하면서 안되면 줄여나감
  for (let l = width; l > 0; l--) {
    let [sx, sy] = [x - l + 1, y - l + 1];

    let zeroCnt = zeroBoard[x][y];
    if (sx === 0 && sy > 0) {
      zeroCnt -= zeroBoard[x][sy - 1];
    } else if (sx > 0 && sy === 0) {
      zeroCnt -= zeroBoard[sx - 1][y];
    } else if (sx > 0 && sy > 0) {
      zeroCnt -=
        zeroBoard[sx - 1][y] + zeroBoard[x][sy - 1] - zeroBoard[sx - 1][sy - 1];
    }
    if (zeroCnt <= K) {
      // 가능하므로 바로 넓이 계산해서 반환
      let ret = areaBoard[x][y];
      if (zeroCnt > 0) ret += bluePrints[zeroCnt - 1];
      if (sx === 0 && sy > 0) {
        ret -= areaBoard[x][sy - 1];
      } else if (sx > 0 && sy === 0) {
        ret -= areaBoard[sx - 1][y];
      } else if (sx > 0 && sy > 0) {
        ret -=
          areaBoard[sx - 1][y] +
          areaBoard[x][sy - 1] -
          areaBoard[sx - 1][sy - 1];
      }
      return ret;
    }
  }

  return 0;
}
