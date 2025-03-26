const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const [m, n] = input[0].split(" ").map(Number);
let v = 0n;
const arr = [];
for (let i = 0; i < n; i++) {
  arr.push(Number(input[1 + i]));
  v += BigInt(arr[i]);
}


let answer = 0n;
let rest = v - BigInt(m);
if (rest <= n) {
  answer += BigInt(rest);
} else {
  arr.sort((a, b) => a - b);
  // 분노 나누기
  for (let i = 0; i < n; i++) {
    // 현재 인원까지 포함해서 남은 분노를 균등하게 나누기
    let res = BigInt(Math.min(Math.floor(Number(rest) / (n - i)), arr[i]));
    answer += res * res;
    rest -= res;
  }
}
let div = BigInt(1 << 31) * BigInt(1 << 31) * BigInt(1 << 2);
console.log(String(answer % div));
