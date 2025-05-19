/**
 * 충전하기
 *
 * 세그먼트 트리 사용해서 특정구간에서 가장 작은 빈 포트를 저장. 꽂혀 있다면 n+1.  갱신에 log(n)이 듬
 * 1번행동으로 특정 포트 번호 이상의 포트에서, 비어있는 가장 작은 포트구하는법
 * -> 재귀로 세그먼트 트리를 타고 들어가면서, 조건을 만족하는 포트 번호를 리턴
 * -> port 번호 이하의 포트는 n+1을 리턴하고, -1인 가장 작은 start를 리턴
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, q] = input[0].split(" ").map(Number);
const portState = Array(n + 1).fill(0); // 연결한 시간 저장
// 트리 만들기
let binaryExponent = 0;
while (1 << binaryExponent <= n) {
  binaryExponent++;
}
const segmentTree = Array(1 << binaryExponent).fill(n + 1);
initTree();

let answer = "";
for (let t = 0; t < q; t++) {
  let [command, port] = input[1 + t].split(" ").map(Number);
  answer += act(command, port, t + 1);
  answer += "\n";
}
console.log(answer);

function initTree(start = 1, end = n, node = 1) {
  if (start === end) {
    segmentTree[node] = start;
    return start;
  }

  let mid = Math.floor((start + end) / 2);
  initTree(start, mid, node * 2);
  initTree(mid + 1, end, node * 2 + 1);
  segmentTree[node] = start;
  return start;
}

// 찾는 포트나 번호가 더 높은 포트 중에 빈 포트 찾기
function findPort(port, start = 1, end = n, node = 1) {
  if (end < port) return n + 1; // 범위 넘으면 리턴
  if (segmentTree[node] === port) return port; // 바로 찾으면 리턴
  if (segmentTree[node] === n + 1) return n + 1; // 현재 구간에 빈 포트 없으면 리턴
  // leaf
  if (start === end) {
    if (segmentTree[node] < port) return n + 1;
    return segmentTree[node];
  }

  let mid = Math.floor((start + end) / 2);
  let left = findPort(port, start, mid, node * 2);
  if (left >= port && left !== n + 1) return left;
  return findPort(port, mid + 1, end, node * 2 + 1);
}

function connectPort(port, start = 1, end = n, node = 1) {
  if (port < start || end < port) return segmentTree[node]; // port가 없는 구간
  // leaf
  if (start === end) {
    if (start === port) {
      segmentTree[node] = n + 1;
    }
    return segmentTree[node];
  }
  let mid = Math.floor((start + end) / 2);
  segmentTree[node] = Math.min(
    connectPort(port, start, mid, node * 2),
    connectPort(port, mid + 1, end, node * 2 + 1)
  );
  return segmentTree[node];
}

function disconnectPort(port, start = 1, end = n, node = 1) {
  if (port < start || end < port) return segmentTree[node]; // port가 포함되지 않는 구간
  // leaf
  if (start === end) {
    if (start === port) {
      segmentTree[node] = port; // 비움 처리
    }
    return segmentTree[node];
  }

  let mid = Math.floor((start + end) / 2);
  segmentTree[node] = Math.min(
    disconnectPort(port, start, mid, node * 2),
    disconnectPort(port, mid + 1, end, node * 2 + 1)
  );
  return segmentTree[node];
}

function act(command, port, count) {
  if (command === 1) {
    port = findPort(port);
    if (port === n + 1) return -1;
    portState[port] = count;
    connectPort(port);
    return port;
  } else if (command === 2) {
    if (portState[port] > 0) {
      // 꽂혀 있으면
      let ret = portState[port];
      portState[port] = 0;
      disconnectPort(port);
      return ret;
    } else {
      // 안 꽂혀 있음
      return -1;
    }
  }
}
