const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

/**
 * 나올 수 있는 보드판 경우의 수 4^5 * 120 = 1024 * 120 = 122,880, 보드판에서 bfs 돌리면 (5^3)^2 * 4 = 125*125*4 = 62500
 * 경우의 수 다 구해서 탈출 bfs 돌리면 불가능
 *
 * 1. 보드판 경우의 수를 줄이기??
 * 2. 탐색을 최적화하기??
 *
 * 현재까지 구한 최소 시간보다 오래걸리면 넘어가기
 */

let firstCube = [];
for (let i = 0; i < 5; i++) {
  let board = [];
  for (let j = 0; j < 5; j++) {
    board.push(input[i * 5 + j].split(" ").map(Number));
  }
  firstCube.push(board);
}

let distance = Infinity;
loop2: for (let cube of orderingPlates(firstCube)) {
  // 5개 판 순서 정해서 던지기
  // 그러고 각 판을 돌려서 bfs 진행
  for (let i = 0; i < 4; i++) {
    for (let j = 0; j < 4; j++) {
      // 제일 위에 판이랑 제일 밑에 판 정하고 입구 출구 가능한지 체크
      if (!checkGates(cube)) {
        rotate(cube, 4);
        continue;
      }
      // 나머지 2,3,4번째 판 돌리기
      for (let k = 0; k < 4; k++) {
        for (let l = 0; l < 4; l++) {
          for (let m = 0; m < 4; m++) {
            if (distance === 12) break loop2;

            bfs(cube);

            rotate(cube, 3);
          }
          rotate(cube, 2);
        }
        rotate(cube, 1);
      }
      rotate(cube, 4);
    }
    rotate(cube, 0);
  }
}

console.log(distance < Infinity ? distance : -1);

function orderingPlates(cube, depth = 0, selected = new Set([])) {
  if (depth === 5) return [[]];
  let ret = [];

  for (let i = 0; i < 5; i++) {
    if (selected.has(i)) continue;

    let newSelected = new Set(selected);
    newSelected.add(i);
    for (let restCube of orderingPlates(cube, depth + 1, newSelected)) {
      ret.push([
        JSON.parse(JSON.stringify(cube[i])),
        ...JSON.parse(JSON.stringify(restCube)),
      ]);
    }
  }

  return ret;
}

/**
 * 해당 층(k)의 보드를 시계 방향으로 회전
 * @param {number} k
 */
function rotate(cube, k) {
  let plate = [...Array(5)].map(() => Array(5).fill(-1));

  for (let i = 0; i < 5; i++) {
    for (let j = 0; j < 5; j++) {
      plate[j][4 - i] = cube[k][i][j];
    }
  }

  cube[k] = plate;
}

function checkRange(x, y, z) {
  if (x < 0 || x >= 5) return false;
  if (y < 0 || y >= 5) return false;
  if (z < 0 || z >= 5) return false;
  return true;
}

/**
 * 입구랑 출구 체크
 * @param {*} cube
 */
function checkGates(cube) {
  if (cube[0][0][0] !== 1) return false;
  if (cube[4][4][4] !== 1) return false;
  return true;
}

function bfs(cube) {
  const delta = [
    [1, 0, 0],
    [-1, 0, 0],
    [0, 1, 0],
    [0, -1, 0],
    [0, 0, 1],
    [0, 0, -1],
  ];

  let [x, y, z] = [0, 0, 0];
  let [targetX, targetY, targetZ] = [4, 4, 4];
  let queue = [[x, y, z, 0]];
  let visited = [...Array(5)].map(() =>
    [...Array(5)].map(() => Array(5).fill(false)),
  );
  visited[x][y][z] = true;

  while (queue.length > 0) {
    let [curX, curY, curZ, curCount] = queue.shift();
    if (curCount >= distance) {
      return;
    }

    for (let [dx, dy, dz] of delta) {
      let [nx, ny, nz] = [curX + dx, curY + dy, curZ + dz];
      if (!checkRange(nx, ny, nz)) continue;
      if (cube[nx][ny][nz] !== 1) continue;
      if (visited[nx][ny][nz]) continue;
      if (nx === targetX && ny === targetY && nz === targetZ) {
        if (curCount + 1 < distance) distance = curCount + 1;
        return;
      }
      visited[nx][ny][nz] = true;
      queue.push([nx, ny, nz, curCount + 1]);
    }
  }
}
