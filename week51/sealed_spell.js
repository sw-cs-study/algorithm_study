function solution(n, bans) {
  const ascii = {};
  for (let i = 0; i < 26; i++) {
    let k = "a".charCodeAt();
    ascii[String.fromCharCode(k + i)] = i + 1;
  }

  // 주문을 원래 주문서에서의 순서 번호로 변환
  const getNumber = (str) => {
    let result = 0;

    for (let i = 0; i < str.length; i++) {
      result *= 26;
      result += ascii[str[i]];
    }

    return result;
  };

  const alphabet = [...Array(26)].map((v, i) =>
    String.fromCharCode("a".charCodeAt() + i),
  );

  // 순서 번호를 주문으로 변환
  const getSpell = (int) => {
    let result = [];

    while (int > 0) {
      int--;

      let rest = int % 26;
      let share = Math.floor(int / 26);

      result.push(alphabet[rest]);
      int = share;
    }

    return result.reverse().join("");
  };

  // ban 주문들을 실제 주문서 순서 번호로 변환
  bans = bans.map((str) => getNumber(str));
  bans.sort((a, b) => a - b);

  let k = 0; // n번째 주문보다 앞에서 삭제된 주문 수

  for (let i = 0; i < bans.length; i++) {
    if (bans[i] <= n + k) {
      k++;
    } else {
      break;
    }
  }

  let answer = getSpell(n + k);
  return answer;
}
