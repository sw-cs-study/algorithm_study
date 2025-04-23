### 풀이

알고리즘(틀림)

1. top-down
2. 재귀 함수로 짜는데 특정 구간에서 최솟값으로 행렬 곱셈을 구함
3. 특정 구간은 (탈출 조건 빼고) 무조건 3개로 이루어짐
4. A, B, C가 있을 때 A와 C는 1개 이상의 행렬들의 연산을 하고 나온 최솟값이다. B는 하나의 행렬이다. 여기서 B가 A행렬 먼저 연산 될수도 있고, C행렬에 먼저 연산 될 수 있다. 이 두 가지 경우를 메인 로직으로 한다. ⇒ a b c d 일때 a * b와 c * d 먼저 하는 연산이 제외됨

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const n = Number(input[0]);
const matrixes = [];
for (let i = 0; i < n; i++) {
  matrixes.push(input[1 + i].split(" ").map(Number));
}
const visited = [];
for (let i = 0; i < n; i++) {
  visited.push([]);
  for (let j = 0; j < n; j++) {
    visited[i].push(Infinity);
  }
}

console.log(recur());

function recur(s = 0, e = n - 1) {
  if (s === e) { return 0; }
  if (visited[s][e] !== Infinity) { return visited[s][e]; }
  if (e - s === 1) {
    visited[s][e] = matrixes[s][0] * matrixes[s][1] * matrixes[e][1];
    return visited[s][e];
  }

  let minValue = Infinity;
  for (let i = s + 1; i < e; i++) { // 앞 뒤 연산을 나눌 행렬 
    // 앞에 포션에 먼저 현재 행렬(i)을 곱하는 경우
    let ret = recur(s, i - 1) + matrixes[s][0] * matrixes[i - 1][1] * matrixes[i][1] + matrixes[s][0] * matrixes[i][1] * matrixes[e][1] + recur(i + 1, e);
    if (ret < minValue) { minValue = ret; }
    // 뒤에 포션에 먼저 현재 행렬(i)을 곱하는 경우
    ret = recur(s, i - 1) + matrixes[s][0] * matrixes[i][0] * matrixes[e][1] + matrixes[i][0] * matrixes[i][1] * matrixes[e][1] + recur(i + 1, e);
    if (ret < minValue) { minValue = ret; }
  }
  visited[s][e] = minValue;
  return visited[s][e];
}
```

알고리즘

1. 두 그룹으로 나눠서 row-column 값 계산

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const n = Number(input[0]);
const matrixes = [];
for (let i = 0; i < n; i++) {
  matrixes.push(input[1 + i].split(" ").map(Number));
}
const visited = [];
for (let i = 0; i < n; i++) {
  visited.push([]);
  for (let j = 0; j < n; j++) {
    visited[i].push((i === j ? 0 : Infinity));
  }
}

console.log(recur());

function recur(s = 0, e = n - 1) {
  if (visited[s][e] !== Infinity) { return visited[s][e]; }
  if (e - s === 1) {
    visited[s][e] = matrixes[s][0] * matrixes[s][1] * matrixes[e][1];
    return visited[s][e];
  }

  let minValue = Infinity;
  for (let i = s; i < e; i++) {
    let ret = recur(s, i) + recur(i + 1, e) + matrixes[s][0] * matrixes[i][1] * matrixes[e][1];
    if (ret < minValue) { minValue = ret; }
  }
  visited[s][e] = minValue;
  return visited[s][e];
}
```