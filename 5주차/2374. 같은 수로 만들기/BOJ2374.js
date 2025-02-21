const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const numbers = [];
for (let i = 0; i < n; i++) {
  numbers.push(Number(input[1 + i]));
}

let prev = numbers[0]; // 이전 최고 높이
let slide = -1;
let bottom = 0;
let answer = 0;
for (let i = 0; i < n - 1; i++) {
  if (slide === -1 && numbers[i] < numbers[i + 1]) {
    // 내리막에서 오르막 될 때 (하단 변곡점)
    bottom = numbers[i];
    slide = 1;
  } else if (slide === 1 && numbers[i] > numbers[i + 1]) {
    // 오르막에서 내리막 될 때 현재꺼 기준으로 bottom과 계산 (상단 변곡점)
    answer += numbers[i] - bottom;
    bottom = numbers[i];
    slide = -1;
    if (prev < numbers[i]) {
      // 더 높은 이전 상단 변곡점이 없으면 갱신
      prev = numbers[i];
    }
  }
}
// 마지막점 처리
if (slide === 1) {
  // 오르막
  if (prev < numbers[n - 1]) {
    // 마지막 수이 제일 높음
    answer += numbers[n - 1] - bottom;
  } else {
    // 이전에 더 높은 수가 있음
    answer += prev - bottom;
  }
} else if (slide === -1) {
  // 내리막
  bottom = numbers[n - 1];
  answer += prev - bottom;
}
console.log(answer);
