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
