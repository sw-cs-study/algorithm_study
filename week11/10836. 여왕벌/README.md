### 풀이

알고리즘

1. 왼쪽 밑부터 왼쪽 위, 왼쪽 위부터 오른쪽 위까지 무조건 0, 1, 2를 증가하게 주어지므로 왼쪽 벽과 위쪽 벽에 붙어있는 애벌레는 자기보다 오른쪽과 위쪽에 있는 애보다 같거나 작다. 그래서 왼쪽 벽은 그냥 더하고, 위쪽 벽 애들(왼쪽 위 빼고)이 자란만큼 아래 애들도 똑같이 자란다. ⇒ 이것도 터지는데???? 최적화 해야함
2. 누적합으로 찍고 한번에 처리하기

고려할점

1. 날짜수가 100만이라고 가로 세로 크기가 700이라서 깡으로 계산하면 100만 \* 49만이라 터짐

코드 ⇒ 이게 왜통과함 1번

```jsx
const input = require("fs")
  .readFileSync(
    process.platform === "linux" ? "/dev/stdin" : "../../sample.txt"
  )
  .toString()
  .trim()
  .split("\n");

const [m, n] = input[0].split(" ").map(Number);
const arr = Array(m * 2 - 1).fill(1);
for (let i = 0; i < n; i++) {
  let [zero, one, two] = input[1 + i].split(" ").map(Number);
  for (let k = zero; k < zero + one; k++) arr[k] += 1;
  for (let k = zero + one; k < zero + one + two; k++) arr[k] += 2;
}

let answer = ``;
let repeat = ``;
for (let i = m; i < arr.length; i++) {
  repeat += ` ${arr[i]}`;
}
repeat += `\n`;
for (let i = m - 1; i >= 0; i--) {
  answer += `${arr[i]}` + repeat;
}

console.log(answer);
```

2번 코드

```jsx
const input = require("fs")
  .readFileSync(
    process.platform === "linux" ? "/dev/stdin" : "../../sample.txt"
  )
  .toString()
  .trim()
  .split("\n");

const [m, n] = input[0].split(" ").map(Number);
const arr = Array(m * 2 - 1).fill(0);
arr[0] = 1;
for (let i = 0; i < n; i++) {
  let [zero, one, two] = input[1 + i].split(" ").map(Number);
  if (zero < arr.length) arr[zero] += 1;
  if (zero + one < arr.length) arr[zero + one] += 1;
}
for (let i = 1; i < arr.length; i++) {
  arr[i] += arr[i - 1];
}

let answer = ``;
let repeat = ``;
for (let i = m; i < arr.length; i++) {
  repeat += ` ${arr[i]}`;
}
repeat += `\n`;
for (let i = m - 1; i >= 0; i--) {
  answer += `${arr[i]}` + repeat;
}

console.log(answer);
```
