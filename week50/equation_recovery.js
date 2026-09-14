// 100개라서 각 수식이 주어지면 체에 거르듯이 가능한 진법을 저장하기.
// 가능한 진법을 적용했을 때, 답이 다 같으면 값 넣고 하나라도 다르면 ? 넣기
// 진법 후보 줄이는 방법
// 1. 수식에 표현 숫자보단 진법의 수가 큼. 예) 46 -> 최소 7진법

// 걍 8개 밖에 안되니깐 몇 진법인지 가정하고 해당 답 나오는지 확인

// 주의사항 : c는 3자리 가능함. 연산 힘드니깐 10진수로 바꿔서 연산하고 다시 변환하기

function solution(expressions) {
  var calculations = [];
  let possibleDigits = new Set([2, 3, 4, 5, 6, 7, 8, 9]);

  const getAllDigits = (...arr) => {
    let ret = [];
    arr.forEach((v) => {
      while (v > 0) {
        ret.push(v % 10);
        v = Math.floor(v / 10);
      }
    });

    return ret;
  };

  const toDecimal = (number, digit) => {
    let ret = 0;
    let ex = 0;
    let base = 10;
    while (number > 0) {
      let place = number % base;
      if (place >= digit) return 1000000;
      ret += place * Math.pow(digit, ex);
      ex++;
      number = Math.floor(number / base);
    }
    return ret;
  };

  const toDigit = (number, digit) => {
    let ret = 0;
    let ex = 0;
    let base = 10;
    while (number > 0) {
      ret += (number % digit) * Math.pow(base, ex);
      ex++;
      number = Math.floor(number / digit);
    }
    return ret;
  };

  const findDigits = (a, op, b, c) => {
    let [x, y, z] = [a, b, c].map(Number);
    possibleDigits.forEach((digit, _, set) => {
      let [decx, decy, decz] = [x, y, z].map((v) => toDecimal(v, digit));
      if (op === "+") {
        if (decx + decy !== decz) set.delete(digit);
      } else {
        if (decx - decy !== decz) set.delete(digit);
      }
    });

    return;
  };

  const calculate = (a, op, b) => {
    let candidates = [];
    [a, b] = [a, b].map(Number);

    possibleDigits.forEach((digit) => {
      let result;
      if (op === "+") {
        result = toDigit(toDecimal(a, digit) + toDecimal(b, digit), digit);
      } else {
        result = toDigit(toDecimal(a, digit) - toDecimal(b, digit), digit);
      }

      candidates.push(result);
    });
    if (candidates.length === 1) return String(candidates[0]);
    let prev = candidates[0];
    for (let i = 1; i < candidates.length; i++) {
      if (prev !== candidates[i]) return "?";
    }
    return String(prev);
  };

  // 가능한 진법 추리기
  for (let e of expressions) {
    let [a, op, b, _, c] = e.split(" ");
    if (a === "X" || b === "X" || c === "X") {
      calculations.push(e);
      for (let num of getAllDigits(a, b, c)) {
        if (num === "X") continue;
        possibleDigits.forEach((v, _, set) => {
          if (v <= num) set.delete(v);
        });
      }

      continue;
    }

    if (possibleDigits.size === 1) continue;

    findDigits(a, op, b, c);
  }
  console.log(possibleDigits);

  // answer 채우기
  let answer = [];
  calculations.forEach((exp) => {
    let [a, op, b, _, c] = exp.split(" ");

    if (a === "X") {
      if (op === "-") a = calculate(b, "+", c);
      if (op === "+") a = calculate(c, "-", b);
    } else if (b === "X") {
      if (op === "-") b = calculate(a, "-", c);
      if (op === "+") b = calculate(c, "-", a);
    } else if (c === "X") {
      if (op === "-") c = calculate(a, "-", b);
      if (op === "+") c = calculate(a, "+", b);
    }

    answer.push([a, op, b, "=", c].join(" "));
  });

  return answer;
}
