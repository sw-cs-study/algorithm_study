const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let tc = Number(input[0]);
let plus = 1;
let print = [];
for (let a = 0; a < tc; a++) { // 5
  let [n, m, w] = input[plus].split(" ").map(Number);
  // 지점 별 도로와 웜홀 정보 저장소
  const points = [];
  for (let i = 0; i < n + 2; i++) {
    points.push([]);
  }
  for (let i = 1; i < n + 1; i++) {
    points[n + 1].push([0, i]);
  }
  // 도로 정보 저장
  for (let i = 0; i < m; i++) {
    let [s, e, t] = input[plus + 1 + i].split(" ").map(Number);
    points[s].push([t, e]);
    points[e].push([t, s]);
  }
  // 웜홀 정보 저장
  for (let i = 0; i < w; i++) {
    let [s, e, t] = input[plus + 1 + m + i].split(" ").map(Number);
    points[s].push([-t, e]);
  }

  // 벨만 포드
  // 초기화
  const board = [];
  for (let i = 0; i < n + 2; i++) {
    board.push(Infinity);
  }
  board[n + 1] = 0;
  // 알고리즘 처리
  for (let i = 0; i < n; i++) {
    for (let p = 1; p < n + 2; p++) {
      for (let [time, np] of points[p]) {
        if (board[p] === Infinity) { continue; }
        if (board[np] > board[p] + time) {
          board[np] = board[p] + time;
        }
      }
    }
  }
  // 음수 싸이클 찾기
  let answer = "NO";
  loop1: for (let p = 1; p < n + 2; p++) {
    for (let [time, np] of points[p]) {
      if (board[np] > board[p] + time) {
        answer = "YES";
        break loop1;
      }
    }
  }
  print.push(answer);

  plus += 1 + m + w;
}

console.log(print.join('\n'));
