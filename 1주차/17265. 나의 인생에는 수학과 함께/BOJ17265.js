const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const n = Number(input[0]);
arr = [];
for (let i = 1; i < 1 + n; i++) {
  arr.push(input[i].trim().split(" "));
}

const stack = [[0, 0]];
let maxValue = -Infinity;
let minValue = Infinity;
while (stack.length > 0) {
  let [i, j, ...values] = stack.pop();
  if (values.length === 0) { // 초기값, 시작점
    (i + 1 < n && stack.push([i + 1, j, arr[i][j]]));
    (j + 1 < n && stack.push([i, j + 1, arr[i][j]]));
  }
  if (i === n - 1 && j === n - 1) { // 마지막 점 도달
    let v = Number(cal(...values, arr[i][j]));
    if (v > maxValue) { maxValue = v; }
    if (v < minValue) { minValue = v; }
    continue;
  }
  if (values.length === 2) { // 3번째면 계산 가능
    let v = cal(...values, arr[i][j]);
    (i + 1 < n && stack.push([i + 1, j, v]));
    (j + 1 < n && stack.push([i, j + 1, v]));
    continue;
  }
  // value가 1개일 때
  (i + 1 < n && stack.push([i + 1, j, ...values, arr[i][j]]));
  (j + 1 < n && stack.push([i, j + 1, ...values, arr[i][j]]));
}
console.log(`${maxValue} ${minValue}`);

function cal(a, op, b) {
  switch (op) {
    case '+': return String(Number(a) + Number(b));
    case '-': return String(Number(a) - Number(b));
    case '*': return String(Number(a) * Number(b));
  }
}