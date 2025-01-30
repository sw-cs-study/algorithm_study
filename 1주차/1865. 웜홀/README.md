### 풀이1 ⇒ 보류

알고리즘

1. 각 지점을 우선순위 큐를 이용한 bfs로 다 방문함 (지점까지 이동한 시간, 지점)
2. 각 방문 지점에서 갈 수 있는 길(웜홀 포함)로 이동
3. 2번에서 다음에 이동할 점의 정보를 “이동에 걸리는 시간” 값을 기준으로 정렬해 더 적은 시간으로 탐색해야함. 이유는, 시간이 빨라지는 경우가 있다면 웜홀을 타는 순간 시간이 더 빨라질 것이고, 이 경우를 우선적으로 탐색하는 게 맞음.
4. 방문 처리의 경우 도로를 기준으로 방문 처리함. 예를 들어, 2에서 3으로 가면 2⇒3을 방문 처리하고 4⇒3은 아직 방문안한것으로 처리됨.

시간복잡도

점을 가장 빠르게 한번씩 방문하므로 N(500), 그리고 최소 힙을 이용한 우선순위 큐를 사용하므로 2log(N)(18), 따라서 복잡도는 Nlog(N)

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let tc = Number(input[0]);
let plus = 1;
for (let t = 0; t < tc; t++) {
  let [n, m, w] = input[plus].split(" ").map(Number);
  // 지점 별 도로와 웜홀 정보 저장소
  const visited = [false];
  const points = [[]];
  for (let i = 0; i < n + 1; i++) {
    points.push([]);
    visited.push(false);
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

  const pq = [[0, 1]]; // [지점까지 이동한 시간, 지점]
  let answer = "NO";
  loop1: while (pq.length > 0) {
    let [totalTime, point] = heapPop(pq);
    for (let [t, e] of points[point]) {
      if (e === 1) { // 종료조건
        if (totalTime + t < 0) { answer = "YES"; }
        continue;
      }

      if (visited[e]) { continue; }

      heapPush(pq, [totalTime + t, e]);
      visited[e] = true;
    }
  }
  console.log(answer);

  plus += 1 + m + w;
}

function heapPush(arr, v) {
  arr.push(v);
  let idx = arr.length - 1;
  let parent = (idx - 1) % 2;
  while (idx > 0 && arr[parent][0] > v[0]) {
    arr[idx] = [...arr[parent]];
    arr[parent] = v;
    idx = parent;
    parent = (idx - 1) % 2;
  }
  return;
}

function heapPop(arr) {
  let ret = [...arr[0]];
  let tmp = arr.pop();
  if (arr.length === 0) { return ret; }
  arr[0] = tmp;
  let v = [...arr[0]];
  let idx = 0;
  // 양쪽 자식 중에 더 작은애랑 바꾸기
  let child = 2 * idx + 1;
  if (child + 1 < arr.length && arr[child] > arr[child + 1]) { child = child + 1; }
  while (child < arr.length && arr[child][0] < v[0]) {
    arr[idx] = [...arr[child]];
    arr[child] = v;
    idx = child;
    child = 2 * idx + 1;
    if (child + 1 < arr.length && arr[child] > arr[child + 1]) { child = child + 1; }
  }

  return ret;
}
```

코드수정1(힙 잘못 만듬)

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let tc = Number(input[0]);
let plus = 1;
for (let t = 0; t < tc; t++) {
  let [n, m, w] = input[plus].split(" ").map(Number);
  // 지점 별 도로와 웜홀 정보 저장소
  const visited = [false];
  const points = [[]];
  for (let i = 0; i < n + 1; i++) {
    points.push([]);
    visited.push(false);
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

  const pq = [[0, 1]]; // [지점까지 이동한 시간, 지점]
  let answer = "NO";
  loop1: while (pq.length > 0) {
    let [totalTime, point] = heapPop(pq);
    for (let [t, e] of points[point]) {
      if (e === 1) { // 종료조건
        if (totalTime + t < 0) { answer = "YES"; }
        continue;
      }

      if (visited[e]) { continue; }

      heapPush(pq, [totalTime + t, e]);
      visited[e] = true;
    }
  }
  console.log(answer);

  plus += 1 + m + w;
}

function heapPush(arr, v) {
  arr.push(v);
  let idx = arr.length - 1;
  let parent = Math.floor((idx - 1) / 2);
  while (idx > 0 && arr[parent][0] > v[0]) {
    arr[idx] = [...arr[parent]];
    arr[parent] = v;
    idx = parent;
    parent = Math.floor((idx - 1) / 2);
  }
  return;
}

function heapPop(arr) {
  if (arr.length === 1) { return arr.pop(); }
  let ret = [...arr[0]];
  arr[0] = arr.pop();
  // 양쪽 자식 중에 더 작은애랑 바꾸기
  let idx = 0;
  let child = 2 * idx + 1; // 왼쪽 아이
  if (child + 1 < arr.length && arr[child][0] > arr[child + 1][0]) { child = child + 1; }
  while (child < arr.length && arr[child][0] < arr[idx][0]) {
    [arr[idx], arr[child]] = [arr[child], arr[idx]];
    idx = child;
    child = 2 * idx + 1;
    if (child + 1 < arr.length && arr[child] > arr[child + 1]) { child = child + 1; }
  }

  return ret;
}
```

### 풀이2 (다익스트라) (다익스라만 쓰면 틀린 알고리즘)

알고리즘

1. 우선순위 큐를 사용해 각 정점에서 다른 정점으로 가는 최솟값 테이블을 구한다.
2. 그리고 이 테이블에서 1번 ⇒ 정점 ⇒ 1번이 음수를 가지는 지 체크한다.

시간복잡도

1. N^2log(N)
2. N

따라서 N^2log(N)

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let tc = Number(input[0]);
let plus = 1;
let print = [];
for (let a = 0; a < tc; a++) {
  let [n, m, w] = input[plus].split(" ").map(Number);
  // 지점 별 도로와 웜홀 정보 저장소
  const board = [];
  const points = [];
  for (let i = 0; i < n + 1; i++) {
    points.push([]);
    board.push([]);
    if (i === 0) { continue; }
    for (let j = 0; j < n + 1; j++) {
      board[i].push(i === j ? 0 : Infinity);
    }
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

  // 각 정점에서 최소 이동거리 구하기
  for (let i = 1; i < n + 1; i++) {
    const pq = [[0, i]];
    const line = board[i];
    for (let k = 0; k < n; k++) {
      let [time, point] = heapPop(pq);
      line[point] = time;
      for (let [t, np] of points[point]) {
        if (line[np] !== Infinity) { continue; }
        heapPush(pq, [time + t, np]);
      }
    }
  }

  let answer = "NO";

  // 1 => 정점 => 1 음수 싸이클 찾기
  for (let i = 2; i < n + 1; i++) { // 정점
    if (board[1][i] + board[i][1] < 0) {
      answer = "YES";
      break;
    }
  }

  print.push(answer);

  plus += 1 + m + w;
}

console.log(print.join('\n'));

function heapPush(arr, v) {
  arr.push(v);
  let idx = arr.length - 1;
  let parent = Math.floor((idx - 1) / 2);
  while (idx > 0 && arr[parent][0] > v[0]) {
    arr[idx] = [...arr[parent]];
    arr[parent] = v;
    idx = parent;
    parent = Math.floor((idx - 1) / 2);
  }
  return;
}

function heapPop(arr) {
  if (arr.length === 1) { return arr.pop(); }
  let ret = [...arr[0]];
  arr[0] = arr.pop();
  // 양쪽 자식 중에 더 작은애랑 바꾸기
  let idx = 0;
  let child = 2 * idx + 1; // 왼쪽 아이
  if (child + 1 < arr.length && arr[child][0] > arr[child + 1][0]) { child = child + 1; }
  while (child < arr.length && arr[child][0] < arr[idx][0]) {
    [arr[idx], arr[child]] = [arr[child], arr[idx]];
    idx = child;
    child = 2 * idx + 1;
    if (child + 1 < arr.length && arr[child] > arr[child + 1]) { child = child + 1; }
  }

  return ret;
}

```

틀린 이유

다익스트라는 음수 가중치일 경우를 세지 못함. 음수 가중치로 더 빨리 갈 수 있는 경우라도 앞에서 이미 방문 처리를 했다면 방문할 수 없게됨. ⇒ 그럼 간선 기준으로 방문 처리를 하면??

### 풀이3. bfs

알고리즘

1. 같은 도로(시작이랑 끝이 같은 도로) 중 시간이 제일 적게 걸리는 도로를 저장
2. 우선순위 큐를 이용해 돌면서 해당 지점까지 값을 방문 배열에 넣음. 해당 지점에 도착했을 때, 방문 배열 값보다 높으면 짜름. 
3. 싸이클 발생을 막기 위해 지금까지 지나온 점을 저장하는 배열도 필요함. ⇒ N * N^2 * 4byte = 125000000 * 4bytes ⇒ 600MB 안됨 

### 풀이4. 벨만 포드

알고리즘

1. n-1동안 1번 지점을 기준으로 연결됨 지점의 도로를 모두 탐색하며 테이블 갱신
2. n번째 갱신 시 값이 달라지면 음수 싸이클이 존재하므로 YES
3. 각 지점이 모두 연결되어 있다는 보장이 없으므로 각 지점에서 다 돌려야함

시간복잡도

1. O(n*(m+w)) ⇒ 500*2700
2. -
3. O(n*(m+w)*n) ⇒ 500 * 500 * 2700 시간초과

코드(91퍼 시간초과)

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let tc = Number(input[0]);
let plus = 1;
let print = [];
for (let a = 0; a < tc; a++) { // 5
  let [n, m, w] = input[plus].split(" ").map(Number);
  // 지점 별 도로와 웜홀 정보 저장소
  const points = [];
  for (let i = 0; i < n + 1; i++) {
    points.push([]);
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
  const visited = []; // 지나간 지점 체크
  for (let i = 0; i < n + 1; i++) {
    visited.push(false);
  }
  let answer = "NO";
  // 모든 그래프를 다 돌아야함
  loop1: for (let start = 1; start < n + 1; start++) {
    if (visited[start]) { continue; }
    // 초기화
    const board = [];
    for (let i = 0; i < n + 1; i++) {
      board.push(Infinity);
    }
    board[start] = 0;
    // 알고리즘 처리
    for (let i = 0; i < n - 1; i++) {
      for (let p = 1; p < n + 1; p++) {
        for (let [time, np] of points[p]) {
          if (board[np] > board[p] + time) {
            visited[start] = true;
            board[np] = board[p] + time;
          }
        }
      }
    }

    // 음수 싸이클 찾기
    for (let p = 1; p < n + 1; p++) {
      for (let [time, np] of points[p]) {
        if (board[np] > board[p] + time) {
          answer = "YES";
          break loop1;
        }
      }
    }

  }

  print.push(answer);

  plus += 1 + m + w;
}

console.log(print.join('\n'));

```

코드수정1 : 가상의 점 N+1을 만들을 0 시간이 걸리는 도로로 연결 ([웜홀 문제 A-Z](https://www.acmicpc.net/board/view/72995))

이렇게 하면 O((n+1)(m+w+n)) ⇒ 501 * 3201로 괜춘?

이때, 싸이클 생성을 막기 위해 N+1에서 각 지점 방향으로만 가는 도로를 만들고 시작 정점을 N+1로 해야한다.

```jsx
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

```