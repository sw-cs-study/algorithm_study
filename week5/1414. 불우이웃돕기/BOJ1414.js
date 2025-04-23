const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const n = Number(input[0]);
const info = []; // [연결값, 컴1, 컴2]
for (let i = 0; i < n; i++) {
  info.push(input[1 + i].trim());
}
console.log(solve());

function solve() {
  let sum = 0;
  const edges = [];
  for (let i = 0; i < n; i++) {
    sum += toNumber(info[i][i]);
    for (let j = i + 1; j < n; j++) {
      let v = toNumber(info[i][j]);
      let ov = toNumber(info[j][i]);
      sum += v + ov;
      if (ov !== 0 && ov < v) { v = ov; }
      if (v === 0) { v = ov; }
      edges.push([v, i, j]);
    }
  }
  edges.sort((a, b) => a[0] - b[0]); // 오름차순 정렬
  let min = 0;
  const parent = [...Array(n)].map((_, i) => i);
  for (let i = 0; i < n - 1; i++) {
    if (edges.length === 0) { return -1; }
    let [v, a, b] = edges.shift();
    while (v === 0 || find(parent, a) === find(parent, b)) {
      if (edges.length === 0) { return -1; }
      [v, a, b] = edges.shift();
    }
    min += v;
    union(parent, a, b);
  }
  let p = parent[0];
  for (let i = 1; i < n; i++) {
    if (p !== find(parent, i)) { return -1; }
  }
  return sum - min;

}



function toNumber(c) {
  if (c == 0) { return 0; }
  if (c.toUpperCase() === c) { return c.charCodeAt() - 'A'.charCodeAt() + 27; } // 대문자
  return c.charCodeAt() - 'a'.charCodeAt() + 1;
}

function find(arr, x) {
  if (x === arr[x]) { return x; }
  let result = find(arr, arr[x]);
  arr[x] = result;
  return result;
}

function union(arr, x, y) {
  x = find(arr, x);
  y = find(arr, y);
  if (x > y) { arr[x] = y; }
  else { arr[y] = x; }
}