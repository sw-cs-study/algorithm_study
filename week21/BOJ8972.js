/**
 * 구현 ㄱㄱ
 * 1. 종수의 위치를 파악
 * 2. 미친 아두이노들의 위치를 파악
 * 3. 해당 방향으로의 다음 위치를 반환하는 함수 만들기
 * 4. 미친 아두이노가 종수의 위치에 가깝게 이동하는 방향 반환하는 함수 만들기
 * 5. 미친 아두이노 폭발시키는 함수 만들기 => 위치를 체크를 큐로 하면 좋을 것 같지만, 일단 객체로 ㄱㄱ
 *
 * 고려할점
 * 보드가 100 * 100이고, 미친 아두이노 갯수에 제한이 없음. 따라서 총 아두이노 갯수를 10000개로 잡고 계산. 종수의 이동이 최대 100개이므로, 이동할때마다 미친 아두이노를 이동하고 폭발시키면 n(종수이동) * (n * n * 2(이동 + 폭발)) O(n^3)으로 가능.
 * 메모리도 넉넉함
 *
 */

class Pos {
  constructor(x, y) {
    this.x = x;
    this.y = y;
  }

  set(x, y) {
    this.x = x;
    this.y = y;
  }
}

function move(pos, dir) {
  pos.x += direction[dir].x;
  pos.y += direction[dir].y;
}

function getCloseDir(targetPos, curPos) {
  let dir = 5;
  let dis = 10000;
  for (let i = 1; i < 10; i++) {
    let [nx, ny] = [curPos.x + direction[i].x, curPos.y + direction[i].y];
    if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
    let curDis = Math.abs(targetPos.x - nx) + Math.abs(targetPos.y - ny);
    if (curDis < dis) {
      dir = i;
      dis = curDis;
    }
  }
  return dir;
}

function bomb() {
  const visited = {};
  // count
  for (let pos of crazyArduinoPoses) {
    let key = `${pos.x} ${pos.y}`;
    if (visited[key]) {
      visited[key] += 1;
    } else {
      visited[key] = 1;
    }
  }
  // bomb
  let size = crazyArduinoPoses.length;
  for (let i = 0; i < size; i++) {
    let pos = crazyArduinoPoses.shift();
    let key = `${pos.x} ${pos.y}`;
    if (visited[key] > 1) {
      continue;
    }
    crazyArduinoPoses.push(pos);
  }
}

function checkMet() {
  for (let pos of crazyArduinoPoses) {
    if (pos.x === jongsuPos.x && pos.y === jongsuPos.y) return true;
  }
  return false;
}

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");
const [n, m] = input[0].split(" ").map(Number);
const jongsuPos = new Pos(-1, -1);
const crazyArduinoPoses = [];
const direction = {
  1: { x: 1, y: -1 },
  2: { x: 1, y: 0 },
  3: { x: 1, y: 1 },
  4: { x: 0, y: -1 },
  5: { x: 0, y: 0 },
  6: { x: 0, y: 1 },
  7: { x: -1, y: -1 },
  8: { x: -1, y: 0 },
  9: { x: -1, y: 1 },
};

for (let i = 0; i < n; i++) {
  const line = input[1 + i].trim();
  for (let j = 0; j < m; j++) {
    if (line[j] === "I") {
      jongsuPos.set(i, j);
    }
    if (line[j] === "R") {
      crazyArduinoPoses.push(new Pos(i, j));
    }
  }
}

const commands = input[1 + n].trim();
let isMet = false;
let count = 0;
for (let d of commands) {
  count++;
  move(jongsuPos, d);
  if (checkMet()) {
    isMet = true;
    break;
  }
  for (let caPos of crazyArduinoPoses) {
    move(caPos, getCloseDir(jongsuPos, caPos));
  }
  if (checkMet()) {
    isMet = true;
    break;
  }
  bomb();
}

if (isMet) {
  console.log(`kraj ${count}`);
} else {
  const board = [];
  for (let i = 0; i < n; i++) {
    let line = [];
    for (let j = 0; j < m; j++) {
      line.push(".");
    }
    board.push(line);
  }
  board[jongsuPos.x][jongsuPos.y] = "I";
  for (let pos of crazyArduinoPoses) {
    board[pos.x][pos.y] = "R";
  }
  let answer = "";
  for (let line of board) {
    answer += line.join("");
    answer += "\n";
  }
  console.log(answer);
}
